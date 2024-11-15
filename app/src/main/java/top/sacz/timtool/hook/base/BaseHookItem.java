package top.sacz.timtool.hook.base;

import java.lang.reflect.Member;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import top.sacz.timtool.hook.core.factory.ExceptionFactory;

/**
 * 所有hook功能的基础类,都应该要继承这个类
 */
public abstract class BaseHookItem {

    /**
     * 功能名称
     */
    private String itemName;

    /**
     * 是否加载
     */
    private boolean isLoad = false;

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public boolean isLoad() {
        return isLoad;
    }

    public void setIsLoad(boolean isLoad) {
        this.isLoad = isLoad;
    }

    /**
     * 标准hook方法执行前
     */
    protected XC_MethodHook.Unhook hookBefore(Member method, HookAction action) {
        return XposedBridge.hookMethod(method, new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) {
                tryExecute(param, action);
            }
        });
    }

    /**
     * 标准hook方法执行后
     */
    protected XC_MethodHook.Unhook hookAfter(Member method, HookAction action) {
        return XposedBridge.hookMethod(method, new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) {
                tryExecute(param, action);
            }
        });
    }

    /**
     * 带执行优先级的 hook
     *
     * @param priority 越高 执行优先级越高 默认50
     */
    protected XC_MethodHook.Unhook hookAfter(Member method, HookAction action, int priority) {
        return XposedBridge.hookMethod(method, new XC_MethodHook(priority) {
            @Override
            protected void afterHookedMethod(MethodHookParam param) {
                tryExecute(param, action);
            }
        });
    }

    /**
     * 跟上面那个一样
     */
    protected XC_MethodHook.Unhook hookBefore(Member method, HookAction action, int priority) {
        return XposedBridge.hookMethod(method, new XC_MethodHook(priority) {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) {
                tryExecute(param, action);
            }
        });
    }

    /**
     * 真正执行接口方法的地方 ，这么写可以很便捷的捕获异常和子类重写
     */
    protected void tryExecute(XC_MethodHook.MethodHookParam param, HookAction hookAction) {
        try {
            hookAction.call(param);
        } catch (Throwable throwable) {
            ExceptionFactory.add(this, throwable);
        }
    }

    /**
     * hook 动作 指代
     * new XC_MethodHook() {
     * protected void beforeHookedMethod(MethodHookParam param) {
     * action.call(param);
     * }
     * }
     */
    protected interface HookAction {
        void call(XC_MethodHook.MethodHookParam param) throws Throwable;
    }

}

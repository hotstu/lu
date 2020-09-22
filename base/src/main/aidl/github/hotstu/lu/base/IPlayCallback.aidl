// ICallback.aidl
package github.hotstu.lu.base;

// Declare any non-default types here with import statements

interface IPlayCallback {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    Bundle onEvent(int what, String msg);
}

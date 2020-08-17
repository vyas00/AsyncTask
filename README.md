# AsyncTask-Lifecycle
Asynctask lifecycle
This android application describes the lifecycle of an async task in different situations.
What happens to a current running asyncTask if the activity gets destroyed, paused or the application gets killed.
Also it discribes the use of Weak references and Strong references in android and the solves the problem of Memory Leak.
Uses weak reference of the main activity so that memory leaks do not occurs even when the asyncTask is running but the host activity has been destroyed.
Also uses the task.cancel() method of async task to cancel the task

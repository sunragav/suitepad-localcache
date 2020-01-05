# suitepad-localcache

## Note: Because the exported components in this app uses a custom permission defined in the webview app,for security reasons, the [webview app](https://github.com/sunragav/suitepad-weview) should be installed first.

This is local repository and serves stored files for the other apps to consume. In this case, the other app is the [ProxyServer](https://github.com/sunragav/suitepad-proxyserver)  which runs an http server.

To start this project from android studio, edit run-configurations and choose Nothing in the launch options.
![Running FileProvider](https://i.imgur.com/KOoY63m.jpg)


# NOTE: THE RELEASE SIGNING KEY HAS BEEN ADDED JUST FOR THE SAKE OF COMPLETION AND DEMONSTRATION. BECAUSE PROGAURD RULES ARE APPLIED ONLY ON THE RELEASE FLAVOR. THE SIGNING KEY SHOULD BE HIDDEN AND KEPT SECRET FROM OTHERS IN A SECURED PLACE AND ACCESSED VIA CI/CD PROCESS. EACH APP IS SIGNED WITH A DIFFERENT SINGING KEY AS PER THE TASK SPEC.

## The optimized APK size is 1.2 MB and the download size is 904 KB
![FileProvider](https://i.imgur.com/49zSJe9.jpg)

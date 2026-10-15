# Reddit Client Patches 1.4.1

Infinity for Reddit+ 8.3.0 moved its subscription activity call, which caused the
Spoof client patch to fail to match its fingerprint. This release recognizes
the new call and retains the legacy fingerprint for older versions.

The Infinity+ 8.3.0 fix was tested with Morphe by the maintainer before this
independent source was created. This release includes the other patches from
the upstream 1.4.0 baseline.

Add this repository as a remote source in Morphe:
https://github.com/Adiker/reddit-client-patches

This app uses code from Patcheddit. To learn more, visit https://reddit.com/r/patcheddit

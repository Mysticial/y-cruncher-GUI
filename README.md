# y-cruncher GUI
Experimental repo for discussing/developing a proper GUI for y-cruncher.

y-cruncher has gone for 8 years without a proper GUI. The HWBOT Submitter that's bundled with it has a Java dependency and is poorly integrated with the main program.

So this is an experimental repro to discuss and possibly implement a GUI for y-cruncher.


**Motivation:**

y-cruncher has simply become too big of a project for me to handle. I've had numerous requsts for a GUI over the years, but I've always drawn the line just short of it due to a number of reasons:

 - I'm not a UI programmer.
 - Distracts from the main purpose of y-cruncher which is pure number-crunching.
 - Potentially massive time investment.

So rather than never supporting a GUI, I'd like to open up the possibility for others to do so if they are interested.



**Goals:**
 -  Support most or all the features of y-cruncher.
 -  Support for all interactions. (parameter constraints/auto-correction, displaying of warnings, memory estimations, etc...)
 -  Support submissions to HWBOT via the HWBOT API.
 -  Separate binary from y-cruncher.
 -  No 3rd dependencies that require installation.


**Support most or all the features of y-cruncher:**

Support all the features of y-cruncher. Non-public features can be excluded. This includes features present only in the private betas, developer binaries, along with any hidden features and easter eggs.

GUI implementations should make minimal assumptions about sub-features since those will vary between versions of y-cruncher and even between binaries within the same version.

If and how the GUI should do CPU dispatching is up for discussion.


**Support for all interactions:**

One of the major weaknesses of the HWBOT Submitter app is the inability to interact with y-cruncher. Information like warnings, memory estimations, and other diagnostics cannot be passed from y-cruncher back to the UI.

Because these interactions will vary by y-cruncher version and binary, the GUI should not try to imitate y-cruncher. IOW, don't try to copy the formulas used to perform memory calculations. Instead, the GUI should feed inputs into y-cruncher and let y-cruncher return the desired output. Same applies to available sub-options.


**Support submissions to HWBOT via the HWBOT API:**

Ideally, the GUI should completely supercede the HWBOT Submitter app. This means implementing the HWBOT API.

This is one major issue here: The validation.

Ideally, all the HWBOT logic should be kept out of y-cruncher. This means that the GUI will need to handle that along with all the validation and the inherent security by obfuscation. How to do this in a potentially open-sourced setting is up for discussion.


**Separate binary from y-cruncher:**

Due to validations among other reasons, y-cruncher itself will need to remain closed-sourced and the binaries unmodifiable. So the GUI must be kept as a separate file that communicates with y-cruncher.

Possible approaches include: IPC, network stack, or dynamic linking.

The easiest and most portable approach is probably using JSON over the network stack.


**No 3rd dependencies that require installation:**

The current HWBOT Submitter requires Java with JavaFX. This is deal-breaker for a lot of people who hate Java and/or don't want to install anything.

On Windows, the GUI should work out-of-box on fresh installations of Windows 7, 8, and 10.
On Linux, this is less important.


**Others:**

The GUI should have minimal performance impact on y-cruncher. It doesn't have to be particularly efficient. It only needs to be fast enough and not hog too many CPU cycles that y-cruncher should be using instead.

Support for Windows is primary. But Linux is also desired. I don't mind having different apps for each OS.

Licensing is flexable. Open-sourced is preferred but not required.









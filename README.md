

A receipt management system

[![CircleCI](https://circleci.com/gh/JessieJingxuGao/skeleton.svg?style=svg)](https://circleci.com/gh/JessieJingxuGao/skeleton)

============  

A super simple one-page application built on scalable RESTful HTTP servers.
1. Users can type to add a receipt.
2. Uses can take a picture and adjust the auto-detected merchant_name and rececipt_amount to add a receipt. It's implemented by Google OCR Text Detection API. <span style="background-color: #FFFF00">As the processing might take some time, please wait at "type_to_input" modal for a while to get the suggested information retrieved.</span>
3. Users can add & delete tags to each receipt.

============  
It's deploed to AWS using Docker. Link to server: http://ec2-52-34-171-107.us-west-2.compute.amazonaws.com:8080/
- Please open in Chrome. GetUserMedia curently doesn't support FireFox.
- To see how user interaction with "type-to-add-tag"
As Modern browser security restricts scripts running on particular domains to that domain only. If you are developing and encounter errors regarding Mixed Media or Same-origin policy, you can cautiously consider disabling security in your browser for development purposes. To do this on OSX, you must <span style="background-color: #FFFF00">quit Chrome completely and then start Chrome from the command-line with special flags</span>:

~~~~
/Applications/Google\ Chrome.app/Contents/MacOS/Google\ Chrome --allow-running-insecure-content --args --disable-web-security --user-data-dir
~~~~

- To see how user interact with "camera-to-add-receipt"
As Chrome only allows access to devices (the camera) in Secure Contexts. That means SSL-enabled websites, and when developing using localhost.
If you want to allow the access to the camera, you will need to <span style="background-color: #FFFF00">set a special flag (run the following code in terminal)</span>:

~~~~
/Applications/Google\ Chrome.app/Contents/MacOS/Google\ Chrome --unsafely-treat-insecure-origin-as-secure="http://ec2-52-34-171-107.us-west-2.compute.amazonaws.com:8080/" --user-data-dir=/tmp
~~~~


For a video showing the user interaction and the system diagram, please refer to  [this google drive folder](https://drive.google.com/drive/folders/1mZ9SqE72frTdznpMlsI2QLZK19jHCFY9?usp=sharing).


============

Some remaining issues to be fixed:
1. The creation time of each receipt will be changed to month/year/date/time rather than just time.
2. Currrent Text Detection tends to put too many characters in to merchant name (this migtht be solved by later playing with the bounding polygon feature to each description of a captured string of text). Also it cannot deal with the $ sign before the amount.






A Digital Receipt Management System enabled by text recognition   

Circle CI Badge:  
[![CircleCI](https://circleci.com/gh/JessieJingxuGao/skeleton.svg?style=svg)](https://circleci.com/gh/JessieJingxuGao/skeleton)

============  

A super simple one-page application built on scalable RESTful HTTP servers.
1. Users can type to add a receipt.
2. Uses can take a picture and adjust the auto-detected merchant_name and rececipt_amount to add a receipt. It's implemented by Google OCR Text Detection API. <span style="background-color: #FFFF00">As the processing might take some time, please wait at "type_to_input" modal for a while to get the suggested information retrieved.</span>
3. Users can add & delete tags to each receipt.

============  

For videos showing the user interaction, please refer to  [this google drive folder](https://drive.google.com/drive/folders/1mZ9SqE72frTdznpMlsI2QLZK19jHCFY9?usp=sharing). When running on aws, type-to-add-receipt and camera-to-add-receipt will need different commands line to open Chrome in disabled security mode.
Video Links:  
[Local](https://drive.google.com/open?id=1sEnFTT_JGL1DUFZKmlQiEQ00RnAOqJsh)，[AWS](https://drive.google.com/open?id=1Kg0SKRc3qHv15WNZ3K_Vf_9u7Mfg_X-R)


============    

I have stopped the AWS servers due to cost. Please refer to the video clips above for demonstration.     

It's deploed to AWS through Docker image. Link to server: http://ec2-52-34-171-107.us-west-2.compute.amazonaws.com:8080/
- Please open in Chrome. GetUserMedia curently doesn't support FireFox.
- To see how user interact with "type-to-add-receipt":  
As Modern browser security restricts scripts running on particular domains to that domain only. So you can cautiously consider disabling security in your browser. To do this on OSX, you must <span style="background-color: #FFFF00">quit Chrome completely and then start Chrome from the command-line with special flags</span>:
~~~~
/Applications/Google\ Chrome.app/Contents/MacOS/Google\ Chrome --allow-running-insecure-content --args --disable-web-security --user-data-dir
~~~~

Or you can use Firefox with CorsE plug-in.

- To see how user interact with "camera-to-add-receipt":  
As Chrome only allows access to devices (the camera) in Secure Contexts, which means SSL-enabled websites (or using localhost). If you want to allow the access to the camera, you will need to <span style="background-color: #FFFF00">set a special flag (run the following code in terminal)</span>:

~~~~
/Applications/Google\ Chrome.app/Contents/MacOS/Google\ Chrome --unsafely-treat-insecure-origin-as-secure="http://ec2-52-34-171-107.us-west-2.compute.amazonaws.com:8080/" --user-data-dir=/tmp
~~~~

============

Some remaining issues to be fixed:
1. The creation time of each receipt will be changed to month/year/date/time rather than just time.
2. Currrent Text Detection tends to put too many characters in to merchant name (this migtht be solved by later playing with the bounding polygon feature to each description of a captured string of text). Also it cannot deal with the $ sign before the amount.
3. The database is currently local.
4. It would be good to have user identification session later.




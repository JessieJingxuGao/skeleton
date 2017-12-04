

A receipt management system

============

A super simple one-page application built on scalable RESTful HTTP servers.
1. Users can type to add a receipt.
2. Uses can take a picture and adjust the auto-detected merchant_name and rececipt_amount to add a receipt. It's implemented by Google OCR Text Detection API.
3. Users can add & delete tags to each receipt.

============

For a video showing the user interaction, please refer to UserVideo.mp4.
It's deploed to AWS using Docker. Link to server: http://ec2-52-34-107-60.us-west-2.compute.amazonaws.com:8080
- Please open in Chrome. GetUserMedia curently doesn't support FireFox.
For system diagram, please refer to Loop.png.

============

Some remaining problems to be fixed:
1. The craation time of each receipt will be changed to month/year/date/time rather than just time.
2. It shall allow users to input non 2-decimal but any valid number.
3. Currrent Text Detection tends to put too many characters in to merchant name and it cannot deal with the $ sign before the amount.



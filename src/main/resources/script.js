
// 1) for tag using jquery
// Getjson (for @get)to get returned media stream and append to html
// Ajax type: "POST", (for @post) to post

// 2) for create receipt
// the save-receipt and cancel-receipt should activaate post

// Errors from the server should appear somewhere in the receipt-entry node so the user can understand and correct any problems.
// use success:, error methods to raise error
// using swal pretty cool!
// - need to check merchant input cannot be empty
// - doesn't need to check if input is two decimal as we will round to 2-decimal


// restful api is on localhost url
const api = "http://localhost:8080";
// <- do not need a root api URL if this page is served directly by the API


// need to add this: FIND TAGS ASSOCIATED WITH EACH RECEIPT-ID -

// This is the idiomatic way to ensure that JQuery does not
// execute until the page has loaded



function getReceiptsList()
{
    $.getJSON
    (api + "/receipts", function (getReceiptsJson)
        {
            $('#receiptList').empty();
            for (var i = 0; i < getReceiptsJson.length; i++)
            {
                var receipt = getReceiptsJson[i];

                // console.log("---jessie----");
                // console.log(receipt);

                $('#receiptList').append
                (
                    '<div class="row receipt" receipt-row-id="' +
                    receipt.id+
                    '">\n' +
                    // we need to have the receipt-row-id here for later enterTag and saveTag
                    '<div class="col-md-3  ">' + receipt.created + '</div>\n' +
                    '<div class="col-md-3 merchant">' + receipt.merchantName + '</div>\n' +
                    '<div class="col-md-3 amount">' + receipt.value + '</div>\n' +
                    '<div class="col-md-3 tags" tag-row-id="'+
                receipt.id+
                    '">\n' +
                    '<button class="btn btn-info add-tag" onclick="enterTag(this,event)"> + </button>\n'+
                    // "+" icon as button - used to enterTag (adding new tag)
                    '</div>' +
                    // for <div class="col-md-3 tag">
                    '</div>'
                    // for <div class="row receipt">

                );

            }
        }
    )
}

function getTags()
{
    $.getJSON
    (api + "/receipttags", function (getTagsJson)
    {
            // $('#receiptList').empty();
            for (var i = 0; i < getTagsJson.length; i++)
            {
                var tag = getTagsJson[i];

                var tagButtonHtml = '';

                // console.log("---jessie----");
                // console.log(receipt);

                // to generate each of tagButtonHtml string to show tags already attached and to apply untag

                        tagButtonHtml +=
                            '<button ' +
                            'class="btn btn-default tagValue" ' +

                            'receipt-id='+
                            '"' +
                            tag.receiptid +
                            '"'+

                            'tag-string='+
                            '"' +
                            tag.tagstring +
                            '"'+

                            'id='+
                            // use id rather than something else so that we can later select using #
                            '"' +
                            tag.receiptid +
                            '-'+
                            tag.tagstring +
                            '"'+

                            // pay attention to this: untag could happen not only by click that tag, but also input the same tag again,
                            // if so we will need to give each tag button not only receip-id , tag-string but also a combined id to check this

                            // to dissociateTag we need the tag & the receipt-id of the receipt the tag attaches to,
                            // so we define these info in the self/this button element for dissociateTag to call this.tag-string, this.receipt-id

                            'onclick="dissociateTag(this,event)">' +
                            tag.tagstring +
                            '</button>\n';

                            // console.log("---jessie----");
                            // console.log(tagButtonHtml);


                $('.tags[tag-row-id=' + tag.receiptid + ']').prepend(tagButtonHtml);

            }
    }
    )
}



function dissociateTag(a,e)
    // e is the event (=onclick) is this needed???
{
    // defined in the tag button, thus we can use this.receipt-id, this.tag-string here
    var tag = $(a).attr("tag-string");
    var id = $(a).attr("receipt-id");

    // b = $(a).parent();
    // cannot remove the button showing this tag-string <div class="col-md-3 tag">

    $.ajax({
        headers: {
            'Accept': 'application/json', // or */*
            'Cache-Control':'no-cache',
            'Content-Type': 'application/json'
        },
        type: 'PUT',
        url: api + "/tags/" + tag,
        data: JSON.stringify(id),
        // data: id,
            // request body

        // in fact the error here is not important, just ignore this for now
        // JSON.parse: unexpected character at line 1 column 1 of the JSON data

        success: function () {
            $(a).remove();

        },
        // error: function(jqXHR, err){
        //     var responseText = jQuery.parseJSON(jqXHR.responseText);
        //     // alert("error untagging");
        //     alert(responseText);
        // }

        error: function(jqXHR, exception) {
            alert(exception);
            // alert('Uncaught Error.\n' + jqXHR.responseText);

            // if (jqXHR.status === 0) {
            //     alert('Not connect.\n Verify Network.');
            // } else if (jqXHR.status == 404) {
            //     alert('Requested page not found. [404]');
            // } else if (jqXHR.status == 500) {
            //     alert('Internal Server Error [500].');
            // } else if (exception === 'parsererror') {
            //     alert('Requested JSON parse failed.');
            // } else if (exception === 'timeout') {
            //     alert('Time out error.');
            // } else if (exception === 'abort') {
            //     alert('Ajax request aborted.');
            // } else {
            //     alert('Uncaught Error.\n' + jqXHR.responseText);
            // }
        }
    });
}



function enterTag(a,e){
    $(a).parent().prepend
    ('<input class="form-control tag_input" type="text" placeholder="Press enter to save" ' +
        'onkeypress="saveTag(this,event)" >'
    );
}
// The input element should execute a PUT request against your PUT /tags/{tag}
// API when the user strikes the Enter key in the INPUT element.

function saveTag(b,e)
{
    if(e.keyCode==13){
        var tag = $(b).val();
            // value of that input element
        var id = $(b).parent().parent().attr("receipt-row-id");
        // use this way (DOM node property) to transmit variable value across different functions!

        // pay attention to this: untag could happen not only by click that tag, but also input the same tag again,
        // if so we will need to give each tag button not only receip-id , tag-string but also a combined id to check this
        if($("#"+id+"-"+tag).length>0){
            $(b).remove();
            dissociatetag($("#"+id+"-"+tag),'no matter what here')
                // in fact for disssociate, there is no need to have the second parameter
            ;
        }
        else
        {
            $.ajax({
                headers: {
                    'Accept': 'application/json', // or */*
                    'Cache-Control':'no-cache',
                    'Content-Type': 'application/json'
                },
                type: 'PUT',
                url: api+"/tags/"+tag,
                data: JSON.stringify(id),
                success: function(msg){
                    // this is to replace the input with another tag!!
                    $(b).replaceWith(

                    '<button ' +
                    'class="btn btn-default tagValue" ' +

                    'receipt-id='+
                    '"' +
                    id +
                    '"'+

                    'tag-string='+
                    '"' +
                    tag +
                    '"'+

                        'id='+
                        '"' +
                        id +
                        '-'+
                        tag +
                        '"'+

                    'onclick="dissociateTag(this,event)">' + tag +
                    '</button>\n'

                    );
                },
                // error: function(jqXHR,err){
                //     // alert(jqXHR.responseText);
                //     alert("error")

                error: function(jqXHR, exception) {
                    alert(exception);
                    // alert('Uncaught Error.\n' + jqXHR.responseText);

                }
            });
        }
    }
}


// to call the function! previously we only defined the function
$(function () {
        getReceiptsList()
        getTags()
    }
)




// can also later use onclick= in html?????? later!!

// $('#save-receipt').click(function ()
// OTHERSISE it doesn't work -must use onclick in html element
function saveReceiptFun()
{
    console.log("clicked save-receipt")
    merchant = $('#merchant').val();
    amount = $('#amount').val();
    if (merchant == '') {
        swal(
            'Merchant cannot be empty'
        );
        return
    }
    amount = parseFloat(amount).toFixed(2);
    // amount is optional so can be NaN
    // input is set to type "number" thus will not have type error issues ??
    // When you call toFixed on NaN you get "NaN" as well.

    receiptJsonInput = {
        "merchant": merchant,
        "amount": amount
    };
    $.ajax({
        headers: {
            'Accept': 'application/json', // or */*
            'Cache-Control':'no-cache',
            'Content-Type': 'application/json'
        },
        type: "POST",
        url: api + "/receipts",

        data: JSON.stringify(receiptJsonInput),

        success: function () {
            getReceiptsList();
            // refresh currently list
            $('#cancel-receipt').click();
            // same with following
            // $('#merchant').val("");
            // $('#amount').val("");
        },
        error: function () {
            alert('Create receipt Error!')
            // alert(exception);
            swal('Create receipt Error!');
            $('#cancel-receipt').click();

        }
    });
    getReceiptsList();
    // refresh currently list
}
// )



// $('#cancel-receipt').click(function () {
function cancelReceiptFun() {
    $('#merchant').val("");
    $('#amount').val("");
}
// )


// get usermedia, pass usermedia stream to video element in html, and save the track during click to send to backend
var imageCapture;
// teacher used let for both
// var track;

function attachMediaStream(mediaStream) {
    $('video')[0].srcObject = mediaStream;
    // Saving the track allows us to capture a photo
    const track = mediaStream.getVideoTracks()[0];
    imageCapture = new ImageCapture(track);
}

//        function attachMediaStream(mediaStream) {
//            if ("srcObject" in $('video')[0]) {
// // 0,1 is important if we have another video element in html
//                $('video')[0].srcObject = mediaStream
//            } else {
//                $('video')[0].src = window.URL && window.URL.createObjectURL(mediaStream) || mediaStream
//            }
//            $('video')[0].play();
//        // Saving the track allows us to capture a photo
//        const track = mediaStream.getVideoTracks()[0];
//        imageCapture = new ImageCapture(track);
//        }

function startVideo() {
    // the string in the () of getUsermedia could be a var called constraint,
    // we can add video:true if we need video, facing mode=user (mobile) is front camera;
    // exact envirnment in tutorial is rear camera?
    navigator.mediaDevices.getUserMedia({video: {facingMode: { exact: "environment" }}})
        .then(attachMediaStream)
        .catch(error => {
        navigator.mediaDevices.getUserMedia({video: true})
        .then(attachMediaStream)
        .catch(error => {
        console.log('you are fooked');
})
});
}


//     navigator.mediaDevices.getUserMedia({audio: false, video: true})
//         .then(function (stream) {
//             var video = document.querySelector('video');
//             // Older browsers may not have srcObject
//             if ("srcObject" in video) {
//                 video.srcObject = stream;
//                 // now the video element in html has the steam as src/input
//             } else {
//                 // Avoid using this in new browsers, as it is going away.
//                 video.src = window.URL.createObjectURL(stream);
//             }
//             video.onloadedmetadata = function (e) {
//                 video.play();
//             };
//         })
//         .catch(function (err) {
//             console.log(err.name + ": " + err.message);
//         });
// }


// Finally, you must destroy the video DOM element and transfer the suggestions
// from OCR into two user-editable inputs that show the results of the OCR parsing.
// If the user is happy with the results, they should be able to add the new receipt
// with the suggestions (or modify the suggestions, or cancel). It is reasonable to
// re-use your 'Add Receipt' form from A3 here.



// https://stackoverflow.com/questions/4998908/convert-data-uri-to-file-then-append-to-formdata
// function dataURItoBlob(dataURI) {
//     // convert base64/URLEncoded data component to raw binary data held in a string
//     var byteString;
//     if (dataURI.split(',')[0].indexOf('base64') >= 0)
//         byteString = atob(dataURI.split(',')[1]);
//     else
//         byteString = unescape(dataURI.split(',')[1]);
//
//     // separate out the mime component
//     var mimeString = dataURI.split(',')[0].split(':')[1].split(';')[0];
//
//     // write the bytes of the string to a typed array
//     var ia = new Uint8Array(byteString.length);
//     for (var i = 0; i < byteString.length; i++) {
//         ia[i] = byteString.charCodeAt(i);
//     }
//
//     return new Blob([ia], {type:mimeString});
// }

function takeSnapshot() {

// replace camera with picture snapshotted
    $("#photocanvas").attr("style", "display:block;");
    $("#takephoto").attr("style", "display:none;");

    // create a CANVAS element that is same size as the image
    imageCapture.grabFrame()
        .then(
            img =>
    {

            const canvas = document.createElement('canvas');
    // canvas is defined in html just inside of the modal to replace the video element
            canvas.width = img.width;
            canvas.height = img.height;
            canvas.getContext('2d').drawImage(img, 0, 0);
            //     console.log(canvas.toDataURL('image/png'));
            const base64EncodedImageData = canvas.toDataURL('image/png').split(',')[1];
    // dataURL = 'data:image/gif;base64,R0lGODlhAQABAAAAACH5BAEKAAEALAAAAAABAAEAAAICTAEAOw==';
    // blob format, base64 format etc

            $("#camera-receipt-modal").modal('hide');
            $("#add-receipt-modal").modal('show');

                // $("#camera-receipt-modal").hide();
                // $('#camera-receipt-modal .close').click();

            // console.log(base64EncodedImageData);
            // console.log("should have hided camera modal");

    //
    // // write the image to a file
    // String filename = getUniqueName();
    // File outputfile = new File("./" + filename + ".png");
    // ImageIO.write(img, "png", outputfile);
    //
    //
            $.ajax({

                headers:
                    {
                    'Accept': 'application/json',
                    'Content-Type': 'text/plain'
                    },

                url: "/images",
                type: "POST",
                data: base64EncodedImageData,

                // contentType: "text/plain",
                success: function(msg,status, jqXHR) {
                    console.log(msg);
                    // get the return of /images api, ReceiptSuggestionResponse(merchantName, amount);
                    // replace the picture with model string input to let users check

                    $("#camera-receipt-modal").modal('hide');
                    $("#add-receipt-modal").modal('show');


                    var amount = msg["amount"];
                    var merchant = msg["merchantName"]


                    $("#merchant").val(merchant);
                    $("#amount").val(amount);

                    // user channot change this
                    // $('#merchant').attr('placeholder',merchant);
                    // $('#amount').attr('placeholder',amount);

                    // document.getElementsByName('Email')[0].placeholder='new text for email';


                }
                    // , ajax has no other property
            }) // ajax ends here


    // don't need to worry about number of digits, we round it to 2 decimal when doing post to /receipt
                    //    amount = parseFloat(amount).toFixed(2);

// put those in ajax success function
//         .then(response => {
//         $('video').after(`<div>got response: <pre>${JSON.stringify(response)}</pre></div>`);
// })
// .always(() => console.log('request complete'));
    // For debugging, you can uncomment this to see the frame that was captured
    // $('BODY').append(canvas);

    } // function with img captured as input
    ); //after having image

} // takeSnape()

//         success: function (msg,status, jqXHR) {
//             console.log(msg);
//             var merchant = msg["merchantName"];
//             var amount = msg["amount"];
//             hidecamera();
//             $("#merchant").val(merchant);
//             $("#amount").val(amount);
//             showform();
//
//         }


// function takephoto(){
//     var video = document.querySelector('video');
// //     var photo = document.querySelector('img');
//     var canvas = document.querySelector('canvas');
//     var ctx = canvas.getContext('2d');
//     $("#photocanvas").attr("style", "display:block;");
//     $("#takephoto").attr("style", "display:none;");
//
//     //TUTORIAL:  https://www.html5rocks.com/en/tutorials/getusermedia/intro/
//
//     var width = 150;
//     var height = 0;
//     height = video.videoHeight / (video.videoWidth/width);
//     canvas.width = width;
//     canvas.height = height;
//         // to make sure the size of photo and video is the same in the webpage
//
//     ctx.drawImage(video, 0, 0, width, height);
//         // coordinate in the destination canvas at which to place the top-left corner of the source image
//     var dataURL = canvas.toDataURL("");
//     // The HTMLCanvasElement.toDataURL() method returns a data URI containing a
//     // representation of the image in the format specified by the type parameter (defaults to PNG).
//     // img = dataURL
//     // blob = dataURLtoBlob(dataURL);
//     blob = dataURL.split(",")[1]
//     console.log(blob)


//     $.ajax({
//         headers: {
//             'Accept': 'application/json',
//             'Content-Type': 'text/plain'
//         },
//         type: "POST",
//         url: api+"/images",
//         data: blob,
//         dataType: 'json',
//         success: function (msg,status, jqXHR) {
//             console.log(msg);
//             var merchant = msg["merchantName"];
//             var amount = msg["amount"];
//             hidecamera();
//             $("#merchant").val(merchant);
//             $("#amount").val(amount);
//             showform();
//
//         }
//     });
// }














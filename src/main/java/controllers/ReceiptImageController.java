// don't know why this cmd could do this (location of the sample image is put in outer folder)
//Assuming you are running your server locally and you have a file containing a base-64-encoded PNG in receipt_img.png.b64, the following CURL request should succeed:
//
//        curl  -H 'Content-Type: text/plain' --data "@./receipt_img.png.b64" localhost:8080/images
//
//



package controllers;

import api.ReceiptSuggestionResponse;
import com.google.cloud.vision.v1.*;
import com.google.protobuf.ByteString;
import java.math.BigDecimal;
import java.util.Base64;
import java.util.Collections;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import org.hibernate.validator.constraints.NotEmpty;

import static java.lang.System.out;


//for isNumberic
import java.util.regex.*;
import java.util.*;





@Path("/images")
@Consumes(MediaType.TEXT_PLAIN)
@Produces(MediaType.APPLICATION_JSON)
public class ReceiptImageController {
    private final AnnotateImageRequest.Builder requestBuilder;

    public ReceiptImageController() {
        // DOCUMENT_TEXT_DETECTION is not the best or only OCR method available
        Feature ocrFeature = Feature.newBuilder().setType(Feature.Type.TEXT_DETECTION).build();
        this.requestBuilder = AnnotateImageRequest.newBuilder().addFeatures(ocrFeature);

    }


//    https://stackoverflow.com/questions/1102891/how-to-check-if-a-string-is-numeric-in-java

//    public static boolean isNumeric(String str){
//        if (null == str || "".equals(str)) {
//            return false;
//        }
//        Pattern pattern = Pattern.compile("^[-\\+]?[.\\d]*$");
////        Greedy quantifiers
////        X?  X, once or not at all
////        X*  X, zero or more times
////        X+  X, one or more times
////        X{n}    X, exactly n times
//        return pattern.matcher(str).matches();
//    }


    // https://stackoverflow.com/questions/308122/simple-regular-expression-for-a-decimal-with-a-precision-of-2


    public static boolean isNumeric(String str)
    {
        if (null == str || "".equals(str)) {
            return false;
        }

        try
        {
            double d = Double.parseDouble(str);
        }
        catch(NumberFormatException nfe)
        {
            return false;
        }
        return true;
    }

//        public static boolean isNumeric(String str)
//        {
//
//            if (null == str || "".equals(str)) {
//                return false;
//            }
//
//            Pattern pattern = Pattern.compile("^[-\\+]?[.\\d]*$");
//            Matcher isNum = pattern.matcher(str);
//            if( !isNum.matches() )
//            {
//                return false;
//            }
//            return true;
//        }


    //Regular Expression (Regexe) in Java- includes two-digital
// ^ to start
// $:Checks if a line end follows


    /**
     * This borrows heavily from the Google Vision API Docs.  See:
     * https://cloud.google.com/vision/docs/detecting-fulltext
     *
     * YOU SHOULD MODIFY THIS METHOD TO RETURN A ReceiptSuggestionResponse:
     *
     * public class ReceiptSuggestionResponse {
     *     String merchantName;
     *     String amount;
     * }
     */

    ////  google ocr text detection:          https://cloud.google.com/vision/docs/detecting-text

    @POST
    public ReceiptSuggestionResponse parseReceipt(@NotEmpty String base64EncodedImage) throws Exception
    {
        base64EncodedImage = base64EncodedImage.replaceAll("\\s+","");
//        delete all white spaces
        Image img = Image.newBuilder().setContent(ByteString.copyFrom(Base64.getDecoder().decode(base64EncodedImage))).build();
        AnnotateImageRequest request = this.requestBuilder.setImage(img).build();

        try (ImageAnnotatorClient client = ImageAnnotatorClient.create()) {
            BatchAnnotateImagesResponse responses = client.batchAnnotateImages(Collections.singletonList(request));
            AnnotateImageResponse res = responses.getResponses(0);

            String merchantName = null;
            BigDecimal amount = null;

            // Your Algo Here!!
//// Sort text annotations by bounding polygon.  Top-most non-decimal text is the merchant
//// bottom-most decimal text is the total amount
//
//
////            curl  -H 'Content-Type: text/plain' --data "@./base_64_data.txt" localhost:8080/images
//

//            for checking
            for (EntityAnnotation annotation : res.getTextAnnotationsList()) {
                    out.printf("Position : %s\n", annotation.getBoundingPoly());
//                location of four corners of that strin
                    out.printf("Text: %s\n", annotation.getDescription());
//                text
                    }


            //sometimes it will read everthing and put into one annotation

            for (EntityAnnotation annotation : res.getTextAnnotationsList()) {
                String name=annotation.getDescription();
                if(!ReceiptImageController.isNumeric(name)){
                    merchantName=name;
                    break;
//                    the first one
//                    so we  don't need to getBoundingPoly() as it will return this from beginning to bottom
                }



            }
            for (EntityAnnotation annotation : res.getTextAnnotationsList()) {
                String anumber=annotation.getDescription();
                System.out.println(anumber);
                if(ReceiptImageController.isNumeric(anumber)){
                    amount = new BigDecimal(anumber);
//                    will keep getting amount and leave with the last one


                }

            }




            return new ReceiptSuggestionResponse(merchantName, amount);
            // don't need to worry about number of digits, we round it to 2 decimal when doing post to /receipt
            //    amount = parseFloat(amount).toFixed(2);

        }
    } //post
} //controller class


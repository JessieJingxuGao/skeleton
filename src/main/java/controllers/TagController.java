package controllers;


import api.ReceiptResponse;
import api.TagResponse;

import dao.TagDao;
import dao.ReceiptDao;


import generated.tables.records.TagsRecord;
import generated.tables.records.ReceiptsRecord;
import generated.tables.records.TagreceiptsRecord;


import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.ArrayList;
import javax.ws.rs.core.Response;

import static java.util.stream.Collectors.toList;

@Path("")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)





public class TagController {



    final ReceiptDao receipts;
    final TagDao tags;


    public TagController(TagDao tags, ReceiptDao receipts)
    {
        this.tags = tags;
        this.receipts = receipts;
    }



//to fulfill the get tags (by or with id) function, we have two ways - we will use option 1
    // - caution: get tags with id is better than by id as that will require an input, so we will not be able to
    // use @get but put using the path /receipt-tag{receipt_id} (/tags/{tag} already occupied)
    // so we will use get tags with ids


    // 1- @get from /receipts also get the tags associated with each receipts, then we will need to modify
    // receiptdao and receiptresponse as the receiptdao will output in format of the new receipt response
    // containing recipt_merchant, receipt_time, receipt_id, receipt_amount & receipt_tag list <string>

    // 2- create a new @ get /receipt-tags to have   (receipt_id,tag_string) then using the receipt_id
    // to add the tag button to each html element; since (receipt_id, tag_string) is from joining two tables, we still
    // need to create a new response TagResponse & create public List<TagResponse> getTagReceiptids()



    @GET
    @Path("/receipttags")
    public List<TagResponse> getReceiptidTags() {
        List<TagResponse> tagreceiptidsResponses = tags.getTagReceiptids();
        // we put this function getTagReceiptids() in the tagdao
        // (TagDao tags, ReceiptDao receipts)
        return tagreceiptidsResponses.stream().collect(toList());
    }





    @PUT
    @Path("/tags/{tag}")
//    @Consumes(MediaType.TEXT_PLAIN)
//    @Produces(MediaType.TEXT_PLAIN)
//otherwise giving untag id value doesn't work'
//Some input files use or override a deprecated API.
    public void toggleTag(@PathParam("tag") String tag, int receiptid) {
//        must be void otherwise will return string to getjson -> cause pare json error

        System.out.println(tag);
        System.out.println(receiptid);

        if (!receipts.idExists(receiptid)) {
            System.out.println( "no such receiptid");
        }

        if (!receipts.idExists(receiptid)) {
            throw new WebApplicationException("This receipt id does not exist", Response.Status.NOT_FOUND);
        }

            // find or create tag by tagname and receiptid
            // first find if exists this tag, if yes, find if there is link to the receipt id, if yes,delete;no,create
            // if no, create this tag, and link this tag to receiptid
            if (tags.tagExists(tag)) {
                int tagid = tags.getTagid(tag);
                if (tags.tagreceiptExists(tagid, receiptid)) {
                    tags.delete(tagid, receiptid);
                    System.out.println( "untag");
                } else {
                    tags.insertTagReceipt(tagid, receiptid);
                    System.out.println( "tag");
                }
            } else {
                int tagid = tags.insert(tag);
                System.out.println("tagid created is");
                System.out.println(tagid);
                tags.insertTagReceipt(tagid, receiptid);
                System.out.println("tag");
            }

        }


    @GET
    @Path("/tags/{tag}")
    public List<ReceiptResponse> getReceiptsFromTag(@NotNull @PathParam("tag") String tag) {
        System.out.println(tag);
        if (!tags.tagExists(tag)) {
            throw new WebApplicationException("this tag does not exist",
                    Response.Status.NOT_FOUND);}

            int tagid = tags.getTagid(tag);
            List<Integer> receiptIDs = receipts.getReceiptIdByTagid(tagid);

            // search through the receipt table to retrieve all the receipt with certain tag id
            List<ReceiptsRecord> ReceiptRecords = new ArrayList<ReceiptsRecord>();

            for (int id : receiptIDs) {
                ReceiptRecords.add(receipts.getReceiptFromID(id));
            }

            return ReceiptRecords.stream().map(ReceiptResponse::new).collect(toList());
        }





}



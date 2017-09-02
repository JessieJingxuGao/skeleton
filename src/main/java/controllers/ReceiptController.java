package controllers;

import api.CreateReceiptRequest;
import api.ReceiptResponse;
import dao.ReceiptDao;
import generated.tables.records.ReceiptsRecord;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Path("/receipts")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ReceiptController {
    final ReceiptDao receipts;

    public ReceiptController(ReceiptDao receipts) {
        this.receipts = receipts;
    }

    @POST
    public int createReceipt(@Valid @NotNull CreateReceiptRequest receipt) {
        return receipts.insert(receipt.merchant, receipt.amount);
//        receipts from receiptdao (db connection)
//        receipt is the parameter...
    }

    @GET
    public List<ReceiptResponse> getReceipts() {
        List<ReceiptsRecord> receiptRecords = receipts.getAllReceipts();
        return receiptRecords.stream().map(ReceiptResponse::new).collect(toList());



//        @PUT
//        @Path("/tags/{tag}")
//        public void toggleTag(@PathParam("tag") String tagName) {
//            // <your code here
//        }

//        def put_tags(rid, tag):
//        url = URL + "/tags/{}".format(tag)
//        d = rid
//        r = requests.put(url, json=d)
//        if not r.ok:
//        print("Failed while PUTting {} with json={!r}".format(url, d))
//        return -1
//        return 0
//
//
//        def get_receipts_by_tag(tag):
//        url = URL + "/tags/{}".format(tag)
//        r = requests.get(url)
//        if not r.ok:
//        print("ERROR: Failed while GETting {}".format(url))
//        return -1
//        return r.json()

//

    }
}


//receipts: db connection
//receipt: parameter
package dao;
// invloving this package to announce the following class is a dao object to have the certain functions

import generated.tables.records.TagsRecord;
//only need this for this will be used to return tagid
//if we only need to use dsl(jooq) to manipulate whithin database
//import generated.tables.records.ReceiptsRecord;
import generated.tables.records.TagreceiptsRecord;


import static generated.Tables.*;


import org.jooq.Configuration;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;

import java.math.BigDecimal;
import java.util.List;
import java.util.ArrayList;

import static com.google.common.base.Preconditions.checkState;

import api.TagResponse;




public class TagDao {
    DSLContext dsl;

    public TagDao(Configuration jooqConfig) {
        this.dsl = DSL.using(jooqConfig);
    }

    public int insert(String tag) {
        System.out.println("insert (tag)");
        TagsRecord tagsRecord = dsl
                .insertInto(TAGS, TAGS.TAG)
                .values(tag)
                .returning(TAGS.ID)
                .fetchOne();
//        System.out.println(tag);
        System.out.println("inserted");

        checkState(tagsRecord != null && tagsRecord.getId() != null, "Insert failed");

        return tagsRecord.getId();
    }

    public void insertTagReceipt(int tagid, int receiptid){
        System.out.println("insertTagReceipt");
//        System.out.println(dsl.insertInto(TAGRECEIPTS,TAGRECEIPTS.TAGID,TAGRECEIPTS.RECEIPTSID).values(tagid,receiptid));
        dsl.insertInto(TAGRECEIPTS,TAGRECEIPTS.TAGID,TAGRECEIPTS.RECEIPTSID)
                .values(tagid,receiptid)
                .returning(TAGS.ID)
                .fetchOne();
//        no need to fetchone since there is no return
//        System.out.println(tagid);
//        System.out.println(receiptid);
        System.out.println("inserted");
//        ;
    }

    public boolean tagExists(String tag){
        System.out.println("tagExist");
//        System.out.println(dsl.fetchExists(TAGS,TAGS.TAG.eq(tag)));
        return dsl.fetchExists(TAGS,TAGS.TAG.eq(tag));
    }

    public int getTagid(String tag){
        System.out.println("getTagid");
//        System.out.println(dsl.selectFrom(TAGS).where(TAGS.TAG.eq(tag)).fetchOne().getId());
        return dsl.selectFrom(TAGS).where(TAGS.TAG.eq(tag)).fetchOne().getId();
    }



    public boolean tagreceiptExists(int tagid, int receiptid){
        System.out.println("tagreceiptExist");
//        System.out.println(dsl.fetchExists(TAGRECEIPTS,TAGRECEIPTS.TAGID.eq(tagid).and(TAGRECEIPTS.RECEIPTSID.eq(receiptid))));
        return dsl.fetchExists(TAGRECEIPTS,TAGRECEIPTS.TAGID.eq(tagid).and(TAGRECEIPTS.RECEIPTSID.eq(receiptid)));
    }

    public void delete(int tagid, int receiptid){
        System.out.println("delete(tagreceipts)");
        dsl.delete(TAGRECEIPTS)
                .where(TAGRECEIPTS.TAGID.eq(tagid))
                .and(TAGRECEIPTS.RECEIPTSID.eq(receiptid))
                .execute();
//        System.out.println(tagid);
//        System.out.println(receiptid);
        System.out.println("deleted");
    }
    public List<TagResponse> getTagReceiptids() {
        ArrayList<TagResponse> responses = new ArrayList<>();
        List<TagreceiptsRecord> tagreceiptsRecords = dsl.selectFrom(TAGRECEIPTS).fetch();
        for (TagreceiptsRecord tagreceiptsRecord : tagreceiptsRecords) {
            TagResponse response = new TagResponse(tagreceiptsRecord);
            //this.receiptid will be filled by the initalization
            TagsRecord tagRecord = dsl.selectFrom(TAGS).where(TAGS.ID.eq(tagreceiptsRecord.getTagid())).fetchOne();
            response.attachTag(tagRecord);
// System.out.println(tagRecords);
            responses.add(response);
//            System.out.println(tagRecord.getTag());
//            System.out.println(tagreceiptsRecord.getReceiptsid());
            }
        return responses;
    }


//    public List<TagResponse> getTagReceiptids() {
//        ArrayList<TagResponse> responses = new ArrayList<>();
//
//        List<TagreceiptsRecord> tagreceiptsRecords = dsl.selectFrom(TAGRECEIPTS).fetch();
//        for (TagreceiptsRecord tagreceiptsRecord : tagreceiptsRecords) {
//            List<TagsRecord> tagRecords = dsl.selectFrom(TAGS).where(TAGS.ID.eq(tagreceiptsRecord.getReceiptsid())).fetch();
//            System.out.println(tagRecords);
//            for (TagsRecord tagRecord : tagRecords) {
//                TagResponse response = new TagResponse(tagreceiptsRecord);
//                //this.receiptid will be filled by the initalization
//                response.attachTag(tagRecord);
//                responses.add(response);
//                System.out.println(tagRecord.getTag());
//                System.out.println(tagreceiptsRecord.getReceiptsid());
//
//            }
//        }
//        return responses;
//    }




}
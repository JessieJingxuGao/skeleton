package dao;

import api.ReceiptResponse;

import static generated.Tables.RECEIPTS;
import static generated.Tables.TAGS;
import static generated.Tables.TAGRECEIPTS;

import generated.tables.records.ReceiptsRecord;
import generated.tables.records.TagsRecord;
import generated.tables.records.TagreceiptsRecord;

import org.jooq.Configuration;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;
import dao.TagDao;

import java.math.BigDecimal;
import java.util.List;
import java.util.ArrayList;

import static com.google.common.base.Preconditions.checkState;

public class ReceiptDao {
    DSLContext dsl;
    TagDao tags;

    public ReceiptDao(Configuration jooqConfig) {
        this.dsl = DSL.using(jooqConfig);
    }

    public int insert(String merchantName, BigDecimal amount) {
        ReceiptsRecord receiptsRecord = dsl
                .insertInto(RECEIPTS, RECEIPTS.MERCHANT, RECEIPTS.AMOUNT)
                .values(merchantName, amount)
                .returning(RECEIPTS.ID)
                .fetchOne();

        checkState(receiptsRecord != null && receiptsRecord.getId() != null, "Insert failed");

        return receiptsRecord.getId();
    }

    public List<ReceiptsRecord> getAllReceipts() {
        System.out.println(dsl.selectFrom(RECEIPTS).fetch());
        return dsl.selectFrom(RECEIPTS).fetch();
    }

    // return all the receiptsid defined by a tagid (first find the receripts.id
    // by tagid from tagreceipts, then return all the receipts by the receipts.id
    public ReceiptsRecord getReceiptFromID(int id){
        return dsl.selectFrom(RECEIPTS).where(RECEIPTS.ID.eq(id)).fetchOne();
    }

    public List<Integer> getReceiptIdByTagid(int tagid){
        return dsl.selectFrom(TAGRECEIPTS).where(TAGRECEIPTS.TAGID.eq(tagid)).fetch(TAGRECEIPTS.RECEIPTSID);
    }

    public boolean idExists(int receiptId){
        return dsl.fetchExists(RECEIPTS,RECEIPTS.ID.eq(receiptId));
    }
}
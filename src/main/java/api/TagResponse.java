

package api;

import com.fasterxml.jackson.annotation.JsonProperty;

import generated.tables.records.TagsRecord;
import generated.tables.records.ReceiptsRecord;
import generated.tables.records.TagreceiptsRecord;


import java.math.BigDecimal;
import java.sql.Time;
import java.util.List;
import java.util.ArrayList;


public class TagResponse {
    @JsonProperty
    Integer receiptid;

    @JsonProperty
    String tagstring;

//    @JsonIgnore can modify the jason response if needed

    public TagResponse(TagreceiptsRecord dbRecord) {
        this.receiptid = dbRecord.getReceiptsid();
    }

    public void attachTag(TagsRecord dbRecord) {
        this.tagstring=dbRecord.getTag();
    }
}

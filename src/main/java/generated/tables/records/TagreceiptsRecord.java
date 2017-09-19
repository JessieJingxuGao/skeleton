/**
 * This class is generated by jOOQ
 */
package generated.tables.records;


import generated.tables.Tagreceipts;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record2;
import org.jooq.Row2;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@Generated(
	value = {
		"http://www.jooq.org",
		"jOOQ version:3.7.4"
	},
	comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class TagreceiptsRecord extends UpdatableRecordImpl<TagreceiptsRecord> implements Record2<Integer, Integer> {

	private static final long serialVersionUID = 1966520365;

	/**
	 * Setter for <code>public.tagreceipts.tagid</code>.
	 */
	public void setTagid(Integer value) {
		setValue(0, value);
	}

	/**
	 * Getter for <code>public.tagreceipts.tagid</code>.
	 */
	public Integer getTagid() {
		return (Integer) getValue(0);
	}

	/**
	 * Setter for <code>public.tagreceipts.receiptsid</code>.
	 */
	public void setReceiptsid(Integer value) {
		setValue(1, value);
	}

	/**
	 * Getter for <code>public.tagreceipts.receiptsid</code>.
	 */
	public Integer getReceiptsid() {
		return (Integer) getValue(1);
	}

	// -------------------------------------------------------------------------
	// Primary key information
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Record2<Integer, Integer> key() {
		return (Record2) super.key();
	}

	// -------------------------------------------------------------------------
	// Record2 type implementation
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Row2<Integer, Integer> fieldsRow() {
		return (Row2) super.fieldsRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Row2<Integer, Integer> valuesRow() {
		return (Row2) super.valuesRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Integer> field1() {
		return Tagreceipts.TAGRECEIPTS.TAGID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Integer> field2() {
		return Tagreceipts.TAGRECEIPTS.RECEIPTSID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer value1() {
		return getTagid();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer value2() {
		return getReceiptsid();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public TagreceiptsRecord value1(Integer value) {
		setTagid(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public TagreceiptsRecord value2(Integer value) {
		setReceiptsid(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public TagreceiptsRecord values(Integer value1, Integer value2) {
		value1(value1);
		value2(value2);
		return this;
	}

	// -------------------------------------------------------------------------
	// Constructors
	// -------------------------------------------------------------------------

	/**
	 * Create a detached TagreceiptsRecord
	 */
	public TagreceiptsRecord() {
		super(Tagreceipts.TAGRECEIPTS);
	}

	/**
	 * Create a detached, initialised TagreceiptsRecord
	 */
	public TagreceiptsRecord(Integer tagid, Integer receiptsid) {
		super(Tagreceipts.TAGRECEIPTS);

		setValue(0, tagid);
		setValue(1, receiptsid);
	}
}

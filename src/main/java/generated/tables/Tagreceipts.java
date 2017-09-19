/**
 * This class is generated by jOOQ
 */
package generated.tables;


import generated.Keys;
import generated.Public;
import generated.tables.records.TagreceiptsRecord;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.TableImpl;


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
public class Tagreceipts extends TableImpl<TagreceiptsRecord> {

	private static final long serialVersionUID = -6061828;

	/**
	 * The reference instance of <code>public.tagreceipts</code>
	 */
	public static final Tagreceipts TAGRECEIPTS = new Tagreceipts();

	/**
	 * The class holding records for this type
	 */
	@Override
	public Class<TagreceiptsRecord> getRecordType() {
		return TagreceiptsRecord.class;
	}

	/**
	 * The column <code>public.tagreceipts.tagid</code>.
	 */
	public final TableField<TagreceiptsRecord, Integer> TAGID = createField("tagid", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

	/**
	 * The column <code>public.tagreceipts.receiptsid</code>.
	 */
	public final TableField<TagreceiptsRecord, Integer> RECEIPTSID = createField("receiptsid", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

	/**
	 * Create a <code>public.tagreceipts</code> table reference
	 */
	public Tagreceipts() {
		this("tagreceipts", null);
	}

	/**
	 * Create an aliased <code>public.tagreceipts</code> table reference
	 */
	public Tagreceipts(String alias) {
		this(alias, TAGRECEIPTS);
	}

	private Tagreceipts(String alias, Table<TagreceiptsRecord> aliased) {
		this(alias, aliased, null);
	}

	private Tagreceipts(String alias, Table<TagreceiptsRecord> aliased, Field<?>[] parameters) {
		super(alias, Public.PUBLIC, aliased, parameters, "");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UniqueKey<TagreceiptsRecord> getPrimaryKey() {
		return Keys.CONSTRAINT_FF;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<UniqueKey<TagreceiptsRecord>> getKeys() {
		return Arrays.<UniqueKey<TagreceiptsRecord>>asList(Keys.CONSTRAINT_FF);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Tagreceipts as(String alias) {
		return new Tagreceipts(alias, this);
	}

	/**
	 * Rename this table
	 */
	public Tagreceipts rename(String name) {
		return new Tagreceipts(name, null);
	}
}

package Message;


import Message.Table;

/**
 *
 * @author Terry
 */
public class TableList 
{
    private final Table[] tables;
    
    public TableList(Table[] tables)
    {
        this.tables = tables;
    } // table
    
    public Table getTable(int number)
    {
        if (number < 1 || number > tables.length)
            return null;
        else return tables[number];
    } // getTable
    
    public int getSize()
    {
        return tables.length - 1;
    } // getSize
    
} // class

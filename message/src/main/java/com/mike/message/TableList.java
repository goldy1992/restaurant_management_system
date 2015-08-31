package com.mike.message;




/**
 *
 * @author Terry
 */
public class TableList 
{
    private Table[] tables;
    
    public TableList(int numOfTables)
    {
        Table[] tables = new Table[numOfTables + 1];
        for (int i = 1; i <= tables.length -1; i++)
//            tables[i] = Table.createTable(i);
        this.tables = tables;
    } // table
    
    public TableList() {
        
    }
    
    public Table getTable(int number)
    {
        if (number < 1 || number > getTables().length)
            return null;
        else return getTables()[number];
    } // getTable
    
    public int getSize()
    {
        return getTables().length - 1;
    } // getSize

    /**
     * @return the tables
     */
    public Table[] getTables() {
        return tables;
    }

    /**
     * @param tables the tables to set
     */
    public void setTables(Table[] tables) {
        this.tables = tables;
    }
    
} // class

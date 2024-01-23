package user;

import system.Cloth;
import system.Electronic;
import system.Product;

import javax.swing.table.AbstractTableModel;
import java.util.List;

class ProductTable extends AbstractTableModel {
    private String[] columnNames; //{"Id", "Name", "Category", "Price", "Info"};
    private List<Product> productList;

    public ProductTable(List<Product> productList, String[] columnNames) {
        this.productList = productList;
        this.columnNames = columnNames;
    }

    @Override
    public int getRowCount() {
        return productList.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Object temp = null;
        Product type = null;
        if(columnIndex == 0){
            temp = productList.get(rowIndex).getProductId();
        }else if (columnIndex == 1){
            temp = productList.get(rowIndex).getProductName();
        }else if (columnIndex == 2){
            type = productList.get(rowIndex);
            if(type instanceof Electronic) temp = "Electronic";
            else if(type instanceof Cloth) temp = "Clothing";
        }else if (columnIndex == 3){
            temp = productList.get(rowIndex).getPrice();
        }else if (columnIndex == 4){
            type = productList.get(rowIndex);
            if(type instanceof Electronic e){
                temp = String.format("Brand: %s, Warranty: %s",
                        e.getBrand(), e.getWarrantyPeriod());
            }
            else if(type instanceof Cloth c){
                temp = String.format("Size: %s, Color: %s",
                        c.getSize(), c.getColor());
            }
        }
        return temp;
    }

    public String getColumnName(int col){
        return columnNames[col];
    }

    public Class getColumnClass(int col){
        if(col == 0){
            return String.class;
        }else if (col == 1){
            return String.class;
        }else if (col == 2) {
            return String.class;
        }else if (col == 3) {
            return String.class;
        }else{
            return String.class;
        }
    }

    public Product getProductAt(int row){
        return productList.get(row);
    }
}

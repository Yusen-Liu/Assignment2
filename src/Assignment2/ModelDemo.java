package Assignment2;

import java.util.Vector;

import javax.swing.table.AbstractTableModel;

public class ModelDemo extends AbstractTableModel{
	private Vector TableData;//用来存放表格数据的线性表
	private Vector TableTitle;//表格的 列标题

	public ModelDemo(){
		TableData = new Vector();
		TableTitle= new Vector();
	//这里我们假设当前的表格是 3x3的
	//先初始化 列标题,有3列
		TableTitle.add("第一列");
		TableTitle.add("第二列");
		TableTitle.add("第三列");
	//初始化3行数据，方便起见直接用String数组保存每一行的数据
	//第0行,都显示表格的坐标
		String []Line1 = {"(0,0)","(0,1)","(0,2)"};
	//第1行
		String []Line2 = {"(1,0)","(1,1)","(1,2)"};
	//第2行
		String []Line3 = {"(2,0)","(2,1)","(2,2)"};
	//将数据挂到线性表形成二维的数据表，形成映射
		TableData.add(Line1);
		TableData.add(Line2);
		TableData.add(Line3);
	}

	public int getRowCount(){

		return TableData.size();
	}
	public int getColumnCount(){

		return TableTitle.size();
	}
@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		String LineTemp[] = (String[])this.TableData.get(rowIndex);

		return LineTemp[columnIndex];

	}

}

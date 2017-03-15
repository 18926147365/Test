package test;

public class MazePoint
{
	//坐标
	public int x;
	public int y;
	//上下左右的墙体  false是没有  true是有
	public boolean upper=true;
	public boolean lower=true;
	public boolean left=true;
	public boolean right=true;
	
	public boolean complete=false;//完成度
	
	public MazePoint(int x,int y)
	{
		this.x=x;
		this.y=y;
	}
	@Override
	public boolean equals(Object obj)
	{
		if(obj==null||obj.getClass()!=MazePoint.class)
		{
			return false;
		}
		MazePoint point =(MazePoint) obj;
		if(this.x==point.x&&this.y==point.y){
			return true;
		}
		return false;
	}
	
	
}

package test;


import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;

public class InitMaze
{
	int width = 20;// 宽
	int height = 20;// 高

	List<MazePoint> maze = new ArrayList<MazePoint>();// 已处理过的点 队列
	MazePoint[][] data;//生成完后构建的矩阵对象

	int[][] points =
	{
	{ 1, 0 },
	{ -1, 0 },
	{ 0, 1 },
	{ 0, -1 } };// 周围的四个点

	public InitMaze(int height, int width)// 初始化
	{
		this.height = height;
		this.width = width;
		data = new MazePoint[width][height];
	}

	public void buildMazeData()// 构建数据
	{
		MazePoint currentP = new MazePoint(0, 0);
		maze.add(currentP);// 添加起点
		int pointSize = width * height;// 总长度

		Random r = new Random();

		System.out.println("开始数据构建");
		while (maze.size() < pointSize)
		{
			// System.out.println("现在所在点：" + currentP.x + "," + currentP.y);
			int conn=examineConn(currentP);//检查是否完成连通
			// 已完成所有点的连通
			if (currentP.complete)
			{
				int index = maze.indexOf(currentP);
				if (index != 0)
				{
					currentP = maze.get(index - 1);
					System.out.println("退回到点：" + currentP.x + "," + currentP.y);
					continue;
				}
			}
			while (true)
			{
				int c = r.nextInt(4);// 获取随机数
				if (c <= conn)// 跳过已完成的
				{
					continue;
				}
				int temp_x = currentP.x + points[c][0];
				int temp_y = currentP.y + points[c][1];
				if (legal(temp_x, temp_y))
				{
					MazePoint p = new MazePoint(temp_x, temp_y);
					if (examinePoint(p))
					{
						connectPoint(currentP, p, c);
						currentP = p;
						maze.add(p);

						System.out.println("到达：" + p.x + "," + p.y + "点");
						break;
					}
				}
			}
		}
		for (MazePoint p : maze)//打开入口和出口
		{
			if (p.x == 0 && p.y == 0)//入口
			{
				p.upper = false;
			}
			if (p.x == width - 1 && p.y == height - 1)//出口
			{
				p.lower = false;
			}
		}
		System.out.println("完成数据构建");
	}
	//检查连通程度
	public int examineConn(MazePoint c)
	{
		int conn = 0;
		if (!c.complete)
		{// /检查完成度
			for (int i = 0; i < points.length; i++)
			{
				int temp_x = c.x + points[i][0];
				int temp_y = c.y + points[i][1];
				if (legal(temp_x, temp_y))
				{
					MazePoint p = new MazePoint(temp_x, temp_y);
					if (examinePoint(p))
					{
						conn = i - 1;
						break;
					}
				}
				if (i == 3)
				{
					c.complete = true;
					System.out.println(c.x + "," + c.y
							+ "已完成所有连通");
				}
			}
		}
		return conn;
	}
	// 设置两个方块间的连通
 	public void connectPoint(MazePoint o, MazePoint t, int c)
	{
		if (c == 0)
		{
			o.right = false;
			t.left = false;
		}
		if (c == 1)
		{
			o.left = false;
			t.right = false;
		}
		if (c == 2)
		{
			o.lower = false;
			t.upper = false;
		}
		if (c == 3)
		{
			o.upper = false;
			t.lower = false;
		}
	}

	// 检验坐标的合法
	public boolean legal(int x, int y)
	{
		if (x >= 0 && x < width && y >= 0 && y < height)
		{
			return true;
		}
		return false;
	}

	// 是否连通
	public boolean examinePoint(MazePoint m)
	{
		for (MazePoint p : maze)
		{
			if (p.equals(m))
			{
				return false;
			}
		}
		return true;
	}

	// 获取数据，生成图片
	public void getMaze() throws IOException
	{
		BufferedImage bi = new BufferedImage((width + 2) * 50,
				(height + 2) * 50, BufferedImage.TYPE_INT_BGR);
		Graphics2D g = bi.createGraphics();
		for (MazePoint p : maze)
		{

			g.setColor(Color.RED);
			if (p.upper)
			{// 构建头部的墙体
				g.fillRect((p.x + 1) * 50, (p.y + 1) * 50, 50, 5);
			}
			if (p.left)
			{// 构建左边的墙体
				g.fillRect((p.x + 1) * 50, (p.y + 1) * 50, 5, 50);
			}
			if (p.right)
			{// 构建右边的墙体
				g.fillRect((p.x + 2) * 50, (p.y + 1) * 50, 5, 50);
			}
			if (p.lower)
			{// 构建下边的墙体
				g.fillRect((p.x + 1) * 50, (p.y + 2) * 50, 50, 5);
			}
		}
		File dst = new File("C:\\Users\\Administrator\\Desktop\\插件注册\\插件\\public\\images\\img_13.png");
		ImageIO.write(bi, "jpg", dst);
		System.out.println("完成图片保存");

		System.out.println("加载完成");
	}
	//生成数据矩阵
	public void initData()
	{
		for (MazePoint p : maze)
		{
			data[p.x][p.y] = p;
		}
	}

	// 获取数组数据
	public MazePoint[][] getData()
	{
		return data;
	}

	public static void main(String[] args)
	{
		long start = System.currentTimeMillis();
		System.out.println();

		InitMaze maze = new InitMaze(40, 40);
		maze.buildMazeData();
		long end = System.currentTimeMillis();

		System.out.println("starttime:" + start + ",endtime:" + end + ",cost:"
				+ (end - start));

		try
		{
			maze.getMaze();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}

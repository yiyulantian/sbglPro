package cn.edu.hrbeu.servlet;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cn.edu.hrbeu.util.Constants;

public class AuthImgServlet extends HttpServlet {
	//����ͼ����֤���л����ַ������
	private final Font mFont = new Font("Arial Black", Font.PLAIN, 16);
	//����ͼ����֤��Ĵ�С
	private final int IMG_WIDTH = 55;
	private final int IMG_HEIGTH = 24;
	
	//����һ����ȡ�����ɫ�ķ���
	private Color getRandColor(int fc,int bc){
		Random random = new Random();
		if(fc > 255) fc = 255;
		if(bc > 255) bc=255;
		int r = fc + random.nextInt(bc - fc);
		int g = fc + random.nextInt(bc - fc);
		int b = fc + random.nextInt(bc - fc);
		//�õ������ɫ
		return new Color(r , g , b);
	}
	
	//��дservice��������ɶԿͻ��˵���Ӧ
	@Override
	public void service(HttpServletRequest request, HttpServletResponse response) 
		throws ServletException, IOException {
		//���ý�ֹ����
		response.setHeader("Pragma","No-cache");
		response.setHeader("Cache-Control","no-cache");
		response.setDateHeader("Expires", 0);
		response.setContentType("image/jpeg");
		BufferedImage image = new BufferedImage(IMG_WIDTH , IMG_HEIGTH , BufferedImage.TYPE_INT_RGB);
		Graphics g = image.getGraphics();
		Random random = new Random();
		g.setColor(getRandColor(200 , 250));
		//��䱳��ɫ
		g.fillRect(1, 1, IMG_WIDTH - 1, IMG_HEIGTH - 1);
		//Ϊͼ����֤����Ʊ߿�
		g.setColor(new Color(102 , 102 , 102));
		g.drawRect(0, 0, IMG_WIDTH - 1, IMG_HEIGTH - 1);
		g.setColor(getRandColor(160,200));
		//�����������
		for (int i = 0 ; i < 80 ; i++) {
			int x = random.nextInt(IMG_WIDTH - 1);
			int y = random.nextInt(IMG_HEIGTH - 1);
			int xl = random.nextInt(6) + 1;
			int yl = random.nextInt(12) + 1;
			g.drawLine(x , y , x + xl , y + yl);
		}
		g.setColor(getRandColor(160,200));
		//�����������
		for (int i = 0 ; i < 80 ; i++){
			int x = random.nextInt(IMG_WIDTH - 1);
			int y = random.nextInt(IMG_HEIGTH - 1);
			int xl = random.nextInt(12) + 1;
			int yl = random.nextInt(6) + 1;
			g.drawLine(x , y , x - xl , y - yl);
		}
		//���û����ַ������
		g.setFont(mFont);
		//���ڱ���ϵͳ��ɵ�����ַ�
		String sRand = "";
		for (int i = 0 ; i < 4 ; i++){
			String tmp = getRandomChar();
			sRand += tmp;
			//��ȡ�����ɫ
			g.setColor(new Color(20 + random.nextInt(110)
				,20 + random.nextInt(110)
				,20 + random.nextInt(110)));
			//��ͼƬ�ϻ���ϵͳ��ɵ�����ַ�
			g.drawString(tmp , 10 * i + 5,17);
		}
		
		//��ȡHttpSesssion����
		HttpSession session = request.getSession(true);
		//������ַ����HttpSesssion������ 
		session.setAttribute(Constants.AUTH_IMG , sRand);
		g.dispose();
		//������������ͼƬ
		ImageIO.write(image, "JPEG", response.getOutputStream());
	}
	
	//�����ȡ����ַ���
	private String getRandomChar() {
		//���һ��0��1��2���������
		int rand = (int)Math.round(Math.random() * 2);
		long itmp = 0;
		char ctmp = '\u0000';
		switch (rand){
			//��ɴ�д��ĸ
			case 1:
				itmp = Math.round(Math.random() * 25 + 65);
				ctmp = (char)itmp;
				return String.valueOf(ctmp);
			//���Сд��ĸ
			case 2:
				itmp = Math.round(Math.random() * 25 + 97);
				ctmp = (char)itmp;
				return String.valueOf(ctmp);
			//�������
			default :
				itmp = Math.round(Math.random() * 9);
				return  itmp + "";
		}
	}
}

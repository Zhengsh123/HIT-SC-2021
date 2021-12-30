package P1;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MagicSquare {
//判断是否是幻方
    boolean isLegalMagicSquare(String fileName) throws IOException {
        ReadSquare readSquare = new ReadSquare(fileName).invoke();
        if (readSquare.is()) return false;
        int[][] square = readSquare.getSquare();
        int row=square.length;
        int col=square[0].length;
        int sum = 0;
        for (int i = 0; i < col; i++)
            sum += square[i][i];
        for (int i = 0; i < row; i++) {
            int rowSum = 0;
            int colSum = 0;
            for (int j = 0; j < col; j++) {
                rowSum += square[i][j];
                colSum += square[j][i];
            }
            if (rowSum != sum || colSum != sum) return false;
        }      
        return true;
    }
//生成幻方
    public static boolean generateMagicSquare(int n) {
    	if (n<=0)
    	{
    		System.out.println("The input is not a positive number");
    		return false;
    	}
    	else if(n%2==0)
    	{
    		System.out.println("The input is an even number");
    		return false;
    	}
        int magic[][] = new int[n][n];
        int row = 0, col = n / 2, i, j, square = n * n;//从第一行正中格开始
        for (i = 1; i <= square; i++) {
            magic[row][col] = i;
            if (i % n == 0)
                row++;
            else {
                if (row == 0)
                    row = n - 1;//超出最右格
                else
                    row--;//找上一行
                if (col == (n - 1))
                    col = 0;//超出最底格
                else
                    col++;//放在右上格
            }
        }
        for (i = 0; i < n; i++) {
            for (j = 0; j < n; j++)
                System.out.print(magic[i][j] + "\t");
            System.out.println();
        }
        generateWrite(n, magic);
        return true;
    }
//生成幻方之后写入文件
	private static void generateWrite(int n, int[][] magic) {
		try {
     	   File file = new File("src/P1/txt/6.txt");
     	   // if file doesn't exists, then create it
     	   if (!file.exists()) {
     	    file.createNewFile();
     	   }

     	   FileWriter fw = new FileWriter(file.getAbsoluteFile());
     	   BufferedWriter bw = new BufferedWriter(fw);
     	   for(int m=0;m<n;m++)
     	   {
     		   for(int k=0;k<n;k++)
     		   {
     			  bw.write(Integer.toString(magic[m][k])+'\t');
     		   }
     		   bw.write('\n');
     	   }
     	   bw.close();

     	   System.out.println("Done");

     	  } catch (IOException e) {
     	   e.printStackTrace();
     	  }
	}
//读取txt文件并判断是否合法
    private class ReadSquare {
        private boolean myResult;
        private String fileName;
        private int[][] square;

        public ReadSquare(String fileName) {
            this.fileName = fileName;
        }

        boolean is() {
            return myResult;
        }

        public int[][] getSquare() {
            return square;
        }

		@SuppressWarnings("resource")
		public ReadSquare invoke() throws IOException {
            List<Integer> resList = new ArrayList<>();
            File file = new File(fileName);
            BufferedReader reader = null;
            BufferedReader tempReader=null;
            String line = null;
            reader = new BufferedReader(new FileReader(file));
            tempReader=new BufferedReader(new FileReader(file));
            int row = 0;
            int col = 0;
            if((tempReader.readLine()) == null)
            {
            	System.out.println(fileName+'\t'+"数据集为空");
                myResult=true;
                return this;
            }
            tempReader.close();
            while ((line = reader.readLine()) != null) {
                String[] tempString = line.split("\t");
                String[] judgeString=line.split("");
                
                if(judgeString==tempString)
                {
                    System.out.println(fileName+'\t'+"数据未以\\t分割");
                    myResult=true;
                    return this;
                }
                if (col == 0) {
                    col = tempString.length;
                } else if (col != tempString.length) {
                    System.out.println(fileName+'\t'+"输入的不是方阵");
                    myResult = true;
                    return this;
                }
                for (int i = 0; i < tempString.length; i++) {
                    if (!Character.isDigit(tempString[i].charAt(0))) {
                        System.out.println(fileName + '\t'+"输入的矩阵中有部分数不是整数");
                        myResult = true;
                        return this;
                    }
                    try
                    {
                        resList.add(Integer.valueOf(tempString[i]));
                    }
                    catch(Exception e)
                    {
                        System.out.println(fileName+'\t'+"数据输入格式有问题，如未以\\t分隔等");
                        myResult=true;
                        return this;
                    }
                }
                row++;
            }
            reader.close();
            if (row != col) {
                System.out.println(fileName+'\t'+"输入的并非方阵");
                myResult = true;
                return this;
            }
            square = new int[row][col];
            int index = 0;
            for (int i = 0; i < row; i++) {
                for (int j = 0; j < col; j++) {
                    square[i][j] = Integer.valueOf(resList.get(index));
                }
            }
            myResult = false;
            return this;
        }
    }
    
    public static void main(String[] args) throws IOException {
        MagicSquare magicSquare = new MagicSquare();
        generateMagicSquare(9);
        String filename1 = "src/P1/txt/1.txt";
        String filename2 = "src/P1/txt/2.txt";
        String filename3 = "src/P1/txt/3.txt";
        String filename4 = "src/P1/txt/4.txt";
        String filename5 = "src/P1/txt/5.txt";
        String filename6 = "src/P1/txt/6.txt";
        System.out.println(filename1 +'\t'+ magicSquare.isLegalMagicSquare(filename1));
        System.out.println(filename2 +'\t'+ magicSquare.isLegalMagicSquare(filename2));
        System.out.println(filename3 +'\t'+ magicSquare.isLegalMagicSquare(filename3));
        System.out.println(filename4 +'\t'+ magicSquare.isLegalMagicSquare(filename4));
        System.out.println(filename5 +'\t'+ magicSquare.isLegalMagicSquare(filename5));
        System.out.println(filename6 +'\t'+ magicSquare.isLegalMagicSquare(filename6));  
    }
}


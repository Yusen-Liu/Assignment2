package Assignment2;

import java.io.File;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import java.math.*;



public class k_means_and_k_nn {
	// Array list for k-means
	static ArrayList<Bus> busList = new ArrayList<Bus>();
		// point( clar_volt, clar_ang, amhe_volt, amhe_ang, ...)
	static double [][] volAn = new double [200][19];
	
	// Array list for knn
	static ArrayList<Bus> busList2 = new ArrayList<Bus>();
	static double [][] volAn2 = new double [20][19];
	

	
	static double [][] cluster1 = new double [200][18];
	static double [][] cluster2 = new double [200][18];
	static double [][] cluster3 = new double [200][18];
	static double [][] cluster4 = new double [200][18];
	static int fl1 = 0, fl2 = 0, fl3 = 0, fl4 = 0;
	
	public static void main(String[] args) {
		
		String dataFile = "/Users/Lysen/Documents/computer application/Assignment 2/measurements.csv";
		
		read_data(dataFile);

		kMeanClustering();
		
		
		// Print 4 clusters
		System.out.print("========================================================================");
		System.out.println("\ncluster1:");
		
		for(int i=0;i<cluster1.length;i++){
			for(int j=0; j<cluster1[i].length;j++){
				if( i<fl1 ){
					System.out.print(cluster1[i][j]+"  ");
				}
			}
			if( i<fl1 ){
				System.out.println();
			}
		}
		System.out.print("========================================================================");
		System.out.println("\ncluster2:");
		for(int i=0;i<cluster2.length;i++){
			for(int j=0; j<cluster2[i].length;j++){
				if( i<fl2 ){
					System.out.print(cluster2[i][j]+"  ");
					
				}
			}
			if( i<fl2 ){
				System.out.println();
			}
		}
		System.out.print("========================================================================");
		System.out.println("\ncluster3:");
		for(int i=0;i<cluster3.length;i++){
			for(int j=0; j<cluster3[i].length;j++){
				if( i<fl3 ){
					System.out.print(cluster3[i][j]+"  ");
					
				}
			}
			if( i<fl3 ){
				System.out.println();
			}
		}
		System.out.print("========================================================================");
		System.out.println("\ncluster4:");
		for(int i=0;i<cluster4.length;i++){
			for(int j=0; j<cluster4[i].length;j++){
				if( i<fl4 ){
					System.out.print(cluster4[i][j]+"  ");
					
				}
			}
			if( i<fl4 ){
				System.out.println();
			}
		}
		System.out.println();
		
		
		// kNN
		String dataFile2 = "/Users/Lysen/Documents/computer application/Assignment 2/analog.csv";
		read_data2(dataFile2);
		
		kNN();
		
		System.out.print("========================================================================");
		System.out.println("\nTesting set:");
		for(int i=0;i<volAn2.length;i++){
			for(int j=0; j<volAn2[i].length;j++){
			System.out.print(volAn2[i][j]+"  ");
			}
			System.out.println();
		}
		
		
	}
		
	public static void read_data(String dataFile) {
		
			BufferedReader br = null;
			String line = "";
			String splitBy = ",";
		  
			try {
				br = new BufferedReader(new FileReader(dataFile));
				int a = 0, b = 1;
				double[] param = {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
				while((line = br.readLine()) != null){
						String[] values = line.split(splitBy);	
						
						switch(b){
							case 1:
								param[0] = Double.parseDouble(values[3]);
								break;
							case 2: 
								param[1] = Double.parseDouble(values[3]);
								break;
							case 3: 
								param[2] = Double.parseDouble(values[3]);
								break;
							case 4: 
								param[3] = Double.parseDouble(values[3]);
								break;
							case 5:
								param[4] = Double.parseDouble(values[3]);
								break;
							case 6: 
								param[5] = Double.parseDouble(values[3]);
								break;
							case 7:
								param[6] = Double.parseDouble(values[3]);
								break;
							case 8:
								param[7] = Double.parseDouble(values[3]);
								break;
							case 9:
								param[8] = Double.parseDouble(values[3]);
								break;
							case 10:
								param[9] = Double.parseDouble(values[3]);
								break;
							case 11:
								param[10] = Double.parseDouble(values[3]);
								break;
							case 12:
								param[11] = Double.parseDouble(values[3]);
								break;
							case 13:
								param[12] = Double.parseDouble(values[3]);
								break;
							case 14:
								param[13] = Double.parseDouble(values[3]);
								break;
							case 15:
								param[14] = Double.parseDouble(values[3]);
								break;
							case 16:
								param[15] = Double.parseDouble(values[3]);
								break;
							case 17:
								param[16] = Double.parseDouble(values[3]);
								break;
							case 18:
								param[17] = Double.parseDouble(values[3]);
								break;
						}
						
						b = b + 1;
						if( b > 18){											
							b = 1;
							for(int i=0; i<18; i++){
								volAn[a][i] = param[i];																
							}
							
							busList.add(new Bus(values[2], param));
							a = a + 1 ;	
						}
				}
																																									    	
			// use code from Java exercise IV to create internal database of iris flowers
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

			// populate the values array and the type list from the above created database  
			
			
		}
    
	public static void kMeanClustering() {
		
		int k = 4;
		// arbitrarily assign centroids from within the dataset
		int a[] = {25, 75, 125, 175};
		//int a[] = new int[k];
		//for(int i=0; i<k; i++){
		//	a[i] = (int)(Math.random()*19);
		//}
		 
	   	// calculate distance of each value from the centroids
		double d[] = new double [k];
		double square;
		for(int i=0; i<200; i++){
			for(int p=0; p<k; p++){
				d[p] = 0;
				for(int q=0; q<18; q++){
					square = Math.pow((volAn[i][q]-volAn[a[p]][q]), 2);
					d[p] = d[p] + square;
				}
				d[p] = Math.sqrt(d[p]);
			}
		// place the point into the closest cluster 
			double mind = d[0];
			int min = 0;
			for(int j=0; j<k; j++){
				if(d[j]<mind){
					mind = d[j];
					min = j;
				}
			}
			if ( min==0 ){
				for(int q=0; q<18; q++){
					cluster1[fl1][q]=volAn[i][q];
				}
				volAn[i][18] = 1;
				fl1 = fl1 +1;
			}else{
				if( min==1 ){
					for(int q=0; q<18; q++){
						cluster2[fl2][q]=volAn[i][q];
					}
					volAn[i][18] = 2;
					fl2 = fl2 +1;
				}else{
					if ( min==2 ){
						for(int q=0; q<18; q++){
							cluster3[fl3][q]=volAn[i][q];
						}
						volAn[i][18] = 3;
						fl3 = fl3 +1;
					}else{
						if ( min== 3){
							for(int q=0; q<18; q++){
								cluster4[fl4][q]=volAn[i][q];
							}
							volAn[i][18] = 4;
							fl4 = fl4 +1;
						}
					}
				}
			}
		}
		// calculate the new centroids
	
			// Array for centroids
		double cen[][] = new double[4][18];
			// Array for centroids(loop)
		double cenNew[][] = new double[4][18];
		for(int i=0; i<4; i++){
			for(int j=0; j<18; j++){
				cen[i][j] = 0;
				cenNew[i][j] = 0;
			}
		}
		
		double sum[] = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		
		
		// for cluster1	
		
		for(int i=0; i<18; i++){
			for(int j=0; j<200;j++){
				if( j<fl1 ){
					sum[i] = sum[i] + cluster1[j][i];
				}
			}
		}
		System.out.print("centroid1:");
		for(int i=0;i<18;i++){
			cen[0][i] = sum[i] / fl1;
			System.out.printf("%.2f ",cen[0][i]);
		}
		System.out.println();
		
		
		
		// for cluster2
		for(int i=0; i<18; i++){
			sum[i] = 0;
		}
		for(int i=0; i<18; i++){
			for(int j=0; j<200;j++){
				if( j<fl2 ){
					sum[i] = sum[i] + cluster2[j][i];
				}
			}
		}
		System.out.print("centroid2:");
		for(int i=0;i<18;i++){
			cen[1][i] = sum[i] / fl2;
			System.out.printf("%.2f ",cen[1][i]);
		}
		System.out.println();
		
		
		
		// for cluster3
		for(int i=0; i<18; i++){
			sum[i] = 0;
		}
		for(int i=0; i<18; i++){
			for(int j=0; j<200;j++){
				if( j<fl3 ){
					sum[i] = sum[i] + cluster3[j][i];
				}
			}
		}
		System.out.print("centroid3:");
		for(int i=0;i<18;i++){
			cen[2][i] = sum[i] / fl3;
			System.out.printf("%.2f ",cen[2][i]);
		}
		System.out.println();
		
		
		
		// for cluster4
		for(int i=0; i<18; i++){
			sum[i] = 0;
		}
		for(int i=0; i<18; i++){
			for(int j=0; j<200;j++){
				if( j<fl4 ){
					sum[i] = sum[i] + cluster4[j][i];
				}
			}
		}
		System.out.print("centroid4:");
		for(int i=0;i<18;i++){
			cen[3][i] = sum[i] / fl4;
			System.out.printf("%.2f ",cen[3][i]);
		}
		System.out.println();
		
		
		
		// calculate error between new and arbitrary centroids
		double di[] = new double [k];
		for(int p=0; p<k; p++){
			di[p] = 0;
			for(int q=0; q<18; q++){
				square = Math.pow((cen[p][q]-volAn[a[p]][q]), 2);
				di[p] = di[p] + square;
			}
			di[p] = Math.sqrt(di[p]);
		}
		
		System.out.print("First error:");
		for(int i=0;i<4;i++){
			System.out.printf("%.2f ",di[i]);
		}
		System.out.println();
		System.out.print("========================================================================");
		System.out.println();
		//keep calculation until the error is less than a specified tolerance. clustering is done
		while((di[0]>0.1)||(di[1]>0.1)||(di[2]>0.1)||(di[3]>0.1)){
			
			//empty clusters
			for(int i=0; i<200; i++){
				for(int j=0; j<18; j++){
					cluster1[i][j]=0;
					cluster2[i][j]=0;
					cluster3[i][j]=0;
					cluster4[i][j]=0;
				}
			}
			
			fl1 = 0;fl2 = 0;fl3 = 0;fl4 = 0;
			
			for(int i=0; i<200; i++){				
				for(int p=0; p<k; p++){
					d[p] = 0;
					for(int q=0; q<18; q++){
						square = Math.pow((volAn[i][q]-cen[p][q]), 2);
						d[p] = d[p] + square;
					}
					d[p]= Math.sqrt(d[p]);
				}
				double mind = d[0];
				int min = 0;
				for(int j=0; j<k; j++){
					if(d[j]<mind){
						mind = d[j];
						min = j;
					}
				}

				
				if ( min==0 ){
					for(int q=0; q<18; q++){
						cluster1[fl1][q]=volAn[i][q];
					}
					fl1 = fl1 +1;
				}else{
					if( min==1 ){
						for(int q=0; q<18; q++){
							cluster2[fl2][q]=volAn[i][q];
						}
						fl2 = fl2 +1;
					}else{
						if ( min==2 ){
							for(int q=0; q<18; q++){
								cluster3[fl3][q]=volAn[i][q];
							}
							fl3 = fl3 +1;
						}else{
							if ( min== 3){
								for(int q=0; q<18; q++){
									cluster4[fl4][q]=volAn[i][q];
								}
								fl4 = fl4 +1;
							}
						}
					}
				}
			}

			// calculate new centeroids again
			
			for(int i=0; i<18; i++){
				sum[i] = 0;
			}
			for(int i=0; i<18; i++){
				for(int j=0; j<200;j++){
					if( j<fl1 ){
						sum[i] = sum[i] + cluster1[j][i];
					}
				}
			}
			System.out.print("centroid1:");
			for(int i=0;i<18;i++){
				cenNew[0][i] = sum[i] / fl1;
				System.out.printf("%.2f ",cenNew[0][i]);
			}
			System.out.println();
			
			// for cluster2
			for(int i=0; i<18; i++){
				sum[i] = 0;
			}
			for(int i=0; i<18; i++){
				for(int j=0; j<200;j++){
					if( j<fl2 ){
						sum[i] = sum[i] + cluster2[j][i];
					}
				}
			}
			System.out.print("centroid2:");
			for(int i=0;i<18;i++){
				cenNew[1][i] = sum[i] / fl2;
				System.out.printf("%.2f ",cenNew[1][i]);
			}
			System.out.println();
			
			// for cluster3
			for(int i=0; i<18; i++){
				sum[i] = 0;
			}
			for(int i=0; i<18; i++){
				for(int j=0; j<200;j++){
					if( j<fl3 ){
						sum[i] = sum[i] + cluster3[j][i];
					}
				}
			}
			System.out.print("centroid3:");
			for(int i=0;i<18;i++){
				cenNew[2][i] = sum[i] / fl3;
				System.out.printf("%.2f ",cenNew[2][i]);
			}
			System.out.println();
			
			// for cluster4
			for(int i=0; i<18; i++){
				sum[i] = 0;
			}
			for(int i=0; i<18; i++){
				for(int j=0; j<200;j++){
					if( j<fl4 ){
						sum[i] = sum[i] + cluster4[j][i];
					}
				}
			}
			System.out.print("centroid4:");
			for(int i=0;i<18;i++){
				cenNew[3][i] = sum[i] / fl4;
				System.out.printf("%.2f ",cenNew[3][i]);
			}
			System.out.println();
			
			for(int p=0; p<k; p++){
				di[p] = 0;
				for(int q=0; q<18; q++){
					square = Math.pow((cen[p][q]-cenNew[p][q]), 2);
					di[p] = di[p] + square;
				}
				d[p]= Math.sqrt(d[p]);
			}
			for(int i=0;i<4;i++){
				System.out.printf("error=%.2f ",di[i]);
			}
			System.out.println();
			
			for(int i=0; i<4; i++){
				for(int j=0; j<18; j++){
					cen[i][j] = cenNew[i][j];
				}
			}
		}
		
	}
	
	public static void read_data2(String dataFile){

		
		BufferedReader br = null;
		String line = "";
		String splitBy = ",";
	  
		try {
			br = new BufferedReader(new FileReader(dataFile));
			int a = 0, b = 1;
			double[] param = {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
			while((line = br.readLine()) != null){
					String[] values = line.split(splitBy);	
					
					switch(b){
						case 1:
							param[0] = Double.parseDouble(values[3]);
							break;
						case 2: 
							param[1] = Double.parseDouble(values[3]);
							break;
						case 3: 
							param[2] = Double.parseDouble(values[3]);
							break;
						case 4: 
							param[3] = Double.parseDouble(values[3]);
							break;
						case 5:
							param[4] = Double.parseDouble(values[3]);
							break;
						case 6: 
							param[5] = Double.parseDouble(values[3]);
							break;
						case 7:
							param[6] = Double.parseDouble(values[3]);
							break;
						case 8:
							param[7] = Double.parseDouble(values[3]);
							break;
						case 9:
							param[8] = Double.parseDouble(values[3]);
							break;
						case 10:
							param[9] = Double.parseDouble(values[3]);
							break;
						case 11:
							param[10] = Double.parseDouble(values[3]);
							break;
						case 12:
							param[11] = Double.parseDouble(values[3]);
							break;
						case 13:
							param[12] = Double.parseDouble(values[3]);
							break;
						case 14:
							param[13] = Double.parseDouble(values[3]);
							break;
						case 15:
							param[14] = Double.parseDouble(values[3]);
							break;
						case 16:
							param[15] = Double.parseDouble(values[3]);
							break;
						case 17:
							param[16] = Double.parseDouble(values[3]);
							break;
						case 18:
							param[17] = Double.parseDouble(values[3]);
							break;
					}
					
					b = b + 1;
					if( b > 18){											
						b = 1;
						for(int i=0; i<18; i++){
							volAn2[a][i] = param[i];
							//System.out.print(volAn[a][i]+ " ");								
						}
						//System.out.println();
						busList2.add(new Bus(values[2], param));
						a = a + 1 ;	
					}
			}
																																								    	
		// use code from Java exercise IV to create internal database of iris flowers
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// populate the values array and the type list from the above created database  
		
		
	
		
	}
	
	
	public static void kNN(){
		int k = 10;
		// Calculate distance between objects
		double distance[] = new double[200];
		double square;
		for(int o=0; o<20; o++){
			for(int p=0; p<200; p++){
				distance[p] = 0;
				for(int q=0; q<18; q++){
					square = Math.pow((volAn2[o][q]-volAn[p][q]), 2);
					distance[p] = distance[p] + square;
				}
				distance[p] = Math.sqrt(distance[p]);
			}
		
		
		// Find the k nearest objects
			int order[] = new int[200];
			for(int i=0; i<200; i++){
				order[i] = i;
			}
			for(int i=0; i<distance.length-1; i++){
				for(int j=i+1; j<distance.length; j++){
					if( distance[i] > distance[j] ){
						double temp = distance[i];
						distance[i] = distance[j];
						distance[j] = temp;
					
						int tem = order[i];
						order[i] = order[j];
						order[j] = tem;
					}
				}
			}
			int fl[] = {0, 0, 0, 0};
			for(int i=0; i<k; i++){
				switch((int)volAn[order[i]][18]){
					case 1:
						fl[0] = fl[0] + 1;
						break;
					case 2:
						fl[1] = fl[1] + 1;
						break;
					case 3:
						fl[2] = fl[2] + 1;
						break;
					case 4:
						fl[3] = fl[3] + 1;
						break;
				}

			}
			if((fl[0] >= fl[1])&&(fl[0] >= fl[2])&&(fl[0] >= fl[3])){
				volAn2[o][18] = 1;
			}else if((fl[1] >= fl[0])&&(fl[1] >= fl[2])&&(fl[1] >= fl[3])){
				volAn2[o][18] = 2;
			}else if((fl[2] >= fl[0])&&(fl[2] >= fl[1])&&(fl[2] >= fl[3])){
				volAn2[o][18] = 3;
			}else{
				volAn2[o][18] = 4;
			}
				
		}
		
		
	}
	
	
}




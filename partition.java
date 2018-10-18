package loopInvariants;

public class partition {
    public static int part(int a[], int left, int right) {
    	
    	System.out.println("Partition beginning: ");
        for (int i= 0; i<a.length; i++) {
            System.out.print( a[i] + " ");
            }
        System.out.println();
        
        
        int l = left +1;
        int r = right;
        int temp;
        while (r-l > 1) {
            
          
            while (a[r] > a[left]) {
                r--;
            }
            while (a[l] < a[left]) {
                l++;
            }
            if (l<r){
            temp = a[l];
            a[l] = a[r];
            a[r] = temp;
            }
            else{
            	l--;
            }
           
        }
        
        temp = a[l];
        a[l] = a[left];
        a[left] = temp;
        
        System.out.println("Partition ending: ");
        for (int i= 0; i<a.length; i++) {
            System.out.print( a[i]+ " ");
            }
        System.out.println();
        	
        	
        	System.out.println("mid value" + l);

        return l;
    }
    
    public static int[] subarray (int a[], int start, int end) {
        int temp[] =  new int [end - start + 1];
        for (int i = 0; start <= end; start++, i++) {
            temp[i]=a[start];
        }
        System.out.println("End of subarray: ");
        for (int i= 0; i<temp.length; i++) {
            System.out.print( temp[i]+ " ");
            }
        System.out.println();
        return temp; 
       
    }
    
    public static int select (int a[], int n, int k) {
        if(n == 1) {
            return a[n - 1];
        }
        else {
            int m = part (a, 0, n-1);
            
            
            System.out.println("beginning of select ");
            for (int i= 0; i<a.length; i++) {
                System.out.print( a[i]+ " ");
                }
            System.out.println();
            
            
            if(k == m +1){
                return a[m];   
            }
            
            // 3 1 7 9 
            
            else if (k <= m) {
               // int temp[] = new int [m - 1];
                a = subarray (a, 0, m );
                
                System.out.println("IF k < n: ");
                for (int i= 0; i<a.length; i++) {
                    System.out.print( a[i]+ " ");
                    }
                System.out.println();
                System.out.print("\nSMHU: " +k + m);

                return select(a, m,k);
            }
            else {
               // int temp[] = new int [m - 1];
            	
            	//{3, -2, 6, 8};
            	//
                
                a = subarray (a, m + 1, n-1);
                System.out.println("If k > n ");
                for (int i= 0; i<a.length; i++) {
                    System.out.print( a[i]+ " ");
                    }
                System.out.println();
                System.out.print("\nSMH: " +k + m);
               
                return select(a, m+1, (k - m - 1));
            }
        }
    }
    
    public static void main(String args []) {
      //  int [] a = {3, 6, 2, 9};
        //{3,6,2, 9,7, 4, 1};
        int [] b = {3};
    //  int m = part(a, 0, 3);
    /*    System.out.println("m: " + m + "\narray: "
                            + a[0] + " " + a[1] + " " + a[2]
                            + " " + a[3] + " " //+ a[4]//+" " + a[5] + " " + a[6] + " " 
                            );
       // System.out.println(select (a, 7, 2));
      // System.out.println( select (b, 1, 1));
       /* int [] k = subarray (a, 0, 3);
        System.out.println("array: "
                + k[0] + " " + k[1] + " " + k[2]
                + " " + k[3]);*/
        //int [] a = {5, 2  , 1, 3, -2 , 10, 2, -2, 8};
        //5 2 2 6 1 3 -2 10 8
        //5 2 2 -2 1 3 6 10 8
        //
        
        int [] a = {-3, 4, 7, 6, 5, 8  };
        	//{3, 6, -2, 8};
        	
        	//{1, -2, 5, 3, 4, 0, 9, 7};
      // part(a, 0, 7);
        
        System.out.println("HIHIHIHIHI" + select (a,6, 3));
        for (int i= 0; i<a.length; i++) {
        System.out.print(a[i]+ " ");
        }
    }
}

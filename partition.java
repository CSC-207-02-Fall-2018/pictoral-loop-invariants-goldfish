package loopInvariants;

public class partition {
    public static int part(int a[], int left, int right) {
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
            
            temp = a[l];
            a[l] = a[r];
            a[r] = temp;
        }
        
        temp = a[l];
        a[l] = a[left];
        a[left] = temp;
        
        return l;
    }
    
    public static int[] subarray (int a[], int start, int end) {
        int temp[] =  new int [end - start + 1];
        for (int i = 0; start < end; start++, i++) {
            temp[i]=a[start];
        }
        return temp; 
    }
    
    public static int select (int a[], int n, int k) {
        
        if(n == 1) {
            return a[n - 1];
        }
        else {
            int m = part (a, 0, n-1);
            //System.out.println(m);
            if(k == m +1){
                return a[m];   
            }
            
            
            else if (k <= m) {
               // int temp[] = new int [m - 1];
                a = subarray (a, 0, m );
                System.out.println("array: "
                        + a[0] + " " + a[1] + " " + a[2]
                        + " " + a[3]);
                return select(a, m, k);
            }
            else {
               // int temp[] = new int [m - 1];
                a = subarray (a, m + 1, n - 1);
                return select(a, m, (k - m - 1));
            }
        }
    }
    
    public static void main(String args []) {
        int [] a = {3, 6, 2, 9};
        //{3,6,2, 9,7, 4, 1};
        int [] b = {3};
      int m = part(a, 0, 3);
        System.out.println("m: " + m + "\narray: "
                            + a[0] + " " + a[1] + " " + a[2]
                            + " " + a[3] + " " //+ a[4]//+" " + a[5] + " " + a[6] + " " 
                            );
       // System.out.println(select (a, 7, 2));
      // System.out.println( select (b, 1, 1));
       /* int [] k = subarray (a, 0, 3);
        System.out.println("array: "
                + k[0] + " " + k[1] + " " + k[2]
                + " " + k[3]);*/
    }
}

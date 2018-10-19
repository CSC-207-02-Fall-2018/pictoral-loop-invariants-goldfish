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
		System.out.print( "FIRST l and r: "+ l +" " +r);
		while (r-l > 1) {


			while (a[r] > a[left] && r>left) {
				r--;
			}
			while (a[l] <= a[left] && l<right) {
				l++;
			}
			if (l<r){
				temp = a[l];
				a[l] = a[r];
				a[r] = temp;
			}
			

		}
		System.out.print( "l and r: "+ l +" " +r);
		if (a[r]< a[l]){
			temp = a[r];
			a[r]=a[l];
			a[l]=temp;
		}
		if (a[l]<a[left]) {
			temp = a[l];
			a[l] = a[left];
			a[left] = temp;}
		else if (l!=0){

			l--;
			temp = a[l];
			a[l] = a[left];
			a[left] = temp;

		}

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

				return select(a, m, k);
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

				return select(a, (n - m - 1), (k - m - 1));
			}
		}
	}

	static double median(int a[]) {
		int length = a.length;
		if (length % 2 == 1) {
			return (double) select (a, length, (length - 1) / 2);
		}
		else {
			int x = select (a, length, (length /2) );
			System.out.println("x: " + x);
			int y = select (a, length, (length/2) + 1);
			System.out.println("y: " + y);
			return ((double) x+y)/2;
		}
	}

	static void quicksortKernel (int a[], int left, int right) {
		int m = part(a, left, right);
		if(m > left + 1) {
			quicksortKernel (a, left, m-1);
		}
		if (m < right - 1) {
			quicksortKernel(a, m+1, right);
		}
	}

	static void quicksort (int a[]) {
		quicksortKernel (a, 0, a.length-1);
	}

	static void invariantA (int a[] ){
		int red = 0;
		int white = 0;
		int blue = 0;
		int unsorted = 0;
		int temp;
		while(unsorted < a.length) {
			if(a[unsorted] == 2) {
				temp = a[unsorted];
				a[unsorted] = a[blue];
				a[blue] = temp;
				unsorted++;
			}
			else if(a[unsorted] == 1) {
				temp = a[unsorted];
				a[unsorted] = a[blue];
				a[blue] = temp;
				temp = a[blue];
				a[blue] = a[white];
				a[white] = temp;
				unsorted++;
				blue++;
			}
			else {
				temp = a[unsorted];
				a[unsorted] = a[blue];
				a[blue] = temp;
				temp = a[blue];
				a[blue] = a[white];
				a[white] = temp;
				temp = a[white];
				a[white] = a[red];
				a[red] = temp;
				unsorted++;
				blue++;
				white++;
			}
		}
	}

	static void invariantB(int a []) {
		int red = 0;
		int white = 0;
		int blue = a.length;
		int unsorted = 0;
		int temp;
		while(unsorted < blue) {
			if(a[unsorted] == 2) {
				blue--;
				temp = a[unsorted];
				a[unsorted] = a[blue];
				a[blue] = temp;
			}
			else if(a[unsorted] == 1) {
				temp = a[unsorted];
				a[unsorted] = a[white];
				a[white] = temp;
				unsorted++;
			}
			else {
				temp = a[unsorted];
				a[unsorted] = a[white];
				a[white] = temp;
				temp = a[white];
				a[white] = a[red];
				a[red] = temp;
				unsorted++;
				white++;
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

		int [] a = {1, -2, 5, 3, 4, 0, 9, 7};
		//{3, 6, -2, 8};

		//{1, -2, 5, 3, 4, 0, 9, 7};
		//part(a, 0, 9);
		//invariantA(a);
		System.out.println("HIHIHIHIHI" + median (a));
		// median(a);
		for (int i= 0; i<a.length; i++) {
			System.out.print(a[i]+ " ");
		}
	}
}

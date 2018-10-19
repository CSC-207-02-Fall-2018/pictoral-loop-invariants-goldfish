package loopInvariants;

/**
 * 
 * @author Aidan Danbury and Mai Phuong Vu
 * class partition contains various methods that modify and sort arrays
 */

public class partition {
	 /**partition takes in an array, and places the left value of it in between the segment of
     *    the array that is being partitioned in between values that are smaller than it and
     *    values that are larger than it
     * Preconditions:
     *    @param a, an array of ints
     *    @param left, an int that is the location of the leftmost value of a that
     *       will be partitioned, and the location of the value that will be partitioned on
     *    @param right, an int that is the location of the rightmost value of a that
     *       will be partitioned.
     *    0 <= left <= right <= length of a - 1
     * Postconditions:
     *    left is moved to position l, the position that results when all the elements before l
     *       are smaller or equal to a[left] and all elements after l are larger or equal to left
     *    @return l, the position that value a[left] was moved to.
     */
	public static int part(int a[], int left, int right) {
		if(a.length == 0){
			return 0;
		}
		
		int l;
		int r = right;
		//checks if a is empty
		if (left != right){
		 l = left + 1;
		}
		else {
			return 0;
		}
		int temp;
		//the loop runs until all the elements between right and left have been searched
		while (r-l > 1) {
			//values that are unsorted in relation to a[left] are swapped
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
		
		//code double checks that left was partitioned on correctly
		
		if (a[r] < a[left]){
			temp = a[r];
			a[r] = a[left];
			a[left] = temp;
		}
		
		else if (a[l]<a[left]) {
			temp = a[l];
			a[l] = a[left];
			a[left] = temp;}
		else if (l!=0){

			l--;
			temp = a[l];
			a[l] = a[left];
			a[left] = temp;

		}

		return l;
	}
	
	/**
	 * subarray takes an array and returns a specified segment of that array 
	 * Preconditions:
	 *    @param a, an int array
	 *    @param start, an int that is the initial location of the output segment
	 *    @param end, an int that is the final location of the output segment
	 *    end >= start
	 * Postconditions:
	 *    @return temp, an int array that is of length (end - start + 1) that consists
	 *       of values that correspond to the values in a from positions start to end
	 */

	public static int[] subarray (int a[], int start, int end) {
		int temp[] =  new int [end - start + 1];
		for (int i = 0; start <= end; start++, i++) {
			temp[i]=a[start];
		}
		return temp; 

	}
	
	/**
	 * select returns the value in a of the kth smallest element in int array a
	 * Preconditions:
	 *    @param a, an int array that is the input
	 *    @param n, an int that is the number of elements in a
	 *    @param k, an int that is the desired kth smallest element in a
	 *    k <= n
	 * Postconditions:
	 *    @return an int that is the value in a that is the kth smallest element
	 */

	public static int select (int a[], int n, int k) {
		//base case if there is one element in a
		if(n == 1) {
			return a[n - 1];
		}
		else {
			//partition is used as necessary to sort a
			int m = part (a, 0, n-1);
			//returns if the kth value is the middle result of part
			if(k == m +1){
				return a[m];   
			}
			//case where the kth element is before middle value
			else if (k <= m) {
				a = subarray (a, 0, m);

				return select(a, m, k);
			}
			//case where the kth element is after middle value
			else {

				a = subarray (a, m + 1, n-1);

				return select(a, (n - m - 1), (k - m - 1));
			}
		}
	}
	/**
	 * median returns a double that is the median of a
	 * Preconditions:
	 *    @param a, an int array one wants to find the median of
	 * Postcondtions:
	 *    @return an int that is the median value: the middle value of the set
	 *    if there is an even number, it returns the average of the two middle values
	 */
	static double median(int a[]) {
		int length = a.length;
		if (length == 0){
			return 0;
		}
		if (length % 2 == 1) {
			return (double) select (a, length, (length + 1) / 2);
		}
		else {
			int x = select (a, length, (length /2) );
			int y = select (a, length, (length/2) + 1);
			return ((double) x+y)/2;
		}
	}


	/**
	 * quicksortKernel helps quicksort sort an array
	 * Preconditions:
	 *    @param a, an int array
	 *    @param left, an int that is the position of the leftmost segment of a
	 *       that is to be sorted
	 *    @param right, an int that is the position of the rightmost segment of a
     *       that is to be sorted
	 * Postconditions:
	 *    a is sorted in ascending order
	 */
	static void quicksortKernel (int a[], int left, int right) {
		//quicksortKernel recursively uses partition to quicksort a
		int m = part(a, left, right);
		
		if(m > left + 1) {
			quicksortKernel (a, left, m-1);
		}
		if (m < right - 1) {
			quicksortKernel(a, m+1, right);
		}
	}

	/**
	 * quicksort sorts an array
	 * Preconditions:
	 *    @param a, an int array
	 * Postconditions:
	 *    a is sorted in ascending order
	 */
	static void quicksort (int a[]) {
		quicksortKernel (a, 0, a.length-1);
	}

	/**
	 * invariantA solves the Dutch Flag problem with the red, white, and blue segments of the
	 *    array on the left of the unsorted segment of the array
	 * Preconditions:
	 *    @param a, an int array containing only values 0, 1, and 2
	 *       (representing red, white, and blue respectively)
	 * Postconditions:
	 *    a is sorted
	 */
	static void invariantA (int a[] ){
		 //positions of the segments initialized
		int red = 0;
		int white = 0;
		int blue = 0;
		int unsorted = 0;
		int temp;
		while(unsorted < a.length) {
			//loop finishes when unsorted is empty
			if(a[unsorted] == 2) {
				temp = a[unsorted];
				a[unsorted] = a[blue];
				a[blue] = temp;
				unsorted++;
			}
			//case in which a[unsorted] is white
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
			//case in which a[unsorted] is red
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
	
	/**
     * invariantB solves the Dutch Flag problem with the red and white segments of the
     *    array on the left of the unsorted segment of the array and the blue segment
     *    of the array on the right of unsorted
     * Preconditions:
     *    @param a, an int array containing only values 0, 1, and 2
     *       (representing red, white, and blue respectively)
     * Postconditions:
     *    a is sorted
     */
	static void invariantB(int a []) {
        //positions of the segments initialized
		int red = 0;
		int white = 0;
		int blue = a.length;
		int unsorted = 0;
		int temp;
		//loop finishes when unsorted is empty
		while(unsorted < blue) {
			 //case in which a[unsorted] is blue
			if(a[unsorted] == 2) {
				blue--;
				temp = a[unsorted];
				a[unsorted] = a[blue];
				a[blue] = temp;
			}
			//case in which a[unsorted] is white
			else if(a[unsorted] == 1) {
				temp = a[unsorted];
				a[unsorted] = a[white];
				a[white] = temp;
				unsorted++;
			}
			//case in which a[unsorted] is red
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
		/*TESTINGS*/
		
		
		/*INVERSE, ODD LIST*/
		System.out.println("A is an inverse ordered, odd number lengthed array: ");
		int [] a = {9,8, 7, 6, 5};
		
		//Testing partition
		System.out.println("PARTITION: ");
		int [] a_part = a;
		System.out.println("Middle - partition for A: " + part(a_part, 0, 4));
		System.out.println("A after partition: ");
		for (int i= 0; i<a_part.length; i++) {
			System.out.print(a_part[i]+ " ");
		}
		System.out.println();
		
		//Testing select
		System.out.println("\nSELECT: ");
		int [] a_select = a;
		
		for (int i= 1; i<=a_select.length; i++) {
			System.out.println("#"+i +" smallest " + select(a_select, 5, i));
		}
		
		//Testing median
		int [] a_median = a;
		System.out.println("\nMEDIAN: ");
		System.out.println("median of A: " + median(a_median));
		
		//testing quicksort
		int [] a_sort = a;
		System.out.println("\nQUICK SORT: ");
		quicksort(a_sort);
		for (int i= 0; i<a_sort.length; i++) {
			System.out.print(a_sort[i]+ " ");
		}
		System.out.println();
		
		/*B IS INVERSE ORDERED AND EVEN*/
		System.out.println("\nB is an inversely ordered, even lengthed array: ");
		int [] b = {5, 4, 3, 2};
		
		//testing partition
		System.out.println("PARTITION: ");
		int [] b_part = b;
		System.out.println("Middle - partition for B: " + part(b_part, 0, 3));
		System.out.println("B after partition: ");
		for (int i= 0; i<b_part.length; i++) {
			System.out.print(b_part[i]+ " ");
		}
		System.out.println();
		
		//testing select 
		System.out.println("\nSELECT: ");
		int [] b_select = b;
		
		for (int i= 1; i<=b_select.length; i++) {
			System.out.println("#"+i +" smallest " + select(b_select, 4, i));
		}
		
		//testing median
		int [] b_median = b;
		System.out.println("\nMEDIAN: ");
		System.out.println("median of B: " + median(b_median));
		
		//testing quicksort
		int [] b_sort = b;
		System.out.println("\nQUICK SORT: ");
		quicksort(b_sort);
		for (int i= 0; i<b_sort.length; i++) {
			System.out.print(b_sort[i]+ " ");
		}
		System.out.println();
		
		
		/*C IS ORDERED AND ODD*/
		System.out.println("\nC is an inverse ordered, odd lengthed array: ");
		int [] c = {1, 2, 3, 4, 5};
		
		//testing partition
		System.out.println("PARTITION: ");
		int [] c_part = c;
		System.out.println("Middle - partition for C: " + part(c_part, 0, 4));
		System.out.println("C after partition: ");
		for (int i= 0; i<c_part.length; i++) {
			System.out.print(c_part[i]+ " ");
		}
		System.out.println();
		
		//testing select
		System.out.println("\nSELECT: ");
		int [] c_select = c;
		
		for (int i= 1; i<=c_select.length; i++) {
			System.out.println("#"+i +" smallest " + select(c_select, 5, i));
		}
		
		//testing median
		int [] c_median = c;
		System.out.println("\nMEDIAN: ");
		System.out.println("median of C: " + median(c_median));
		
		//testing quicksort
		int [] c_sort = c;
		System.out.println("\nQUICK SORT: ");
		quicksort(c_sort);
		for (int i= 0; i<c_sort.length; i++) {
			System.out.print(c_sort[i]+ " ");
		}
		System.out.println();
		
		/*D IS ORDERED AND EVEN*/
		System.out.println("\nD is an ordered, even lengthed array: ");
		int [] d = {1, 2, 3, 4};
		
		//testing partition
		System.out.println("PARTITION: ");
		int [] d_part = d;
		System.out.println("Middle - partition for D: " + part(d_part, 0, 3));
		System.out.println("D after partition: ");
		for (int i= 0; i<d_part.length; i++) {
			System.out.print(d_part[i]+ " ");
		}
		System.out.println();
		
		//testing select
		System.out.println("\nSELECT: ");
		int [] d_select = d;
		
		for (int i= 1; i<=d_select.length; i++) {
			System.out.println("#"+i +" smallest " + select(d_select, 4, i));
		}
		
		//testing median
		int [] d_median = d;
		System.out.println("\nMEDIAN: ");
		System.out.println("median of D: " + median(d_median));
		
		//testing quicksort
		int [] d_sort = d;
		System.out.println("\nQUICK SORT: ");
		quicksort(d_sort);
		for (int i= 0; i<d_sort.length; i++) {
			System.out.print(d_sort[i]+ " ");
		}
		System.out.println();
		
		/*E IS NOT SORTED, SMALLEST NUMBER IS THE FIRST ONE*/
		System.out.println("\nE is an ordered array where the first number is the smallest: ");
		int [] e = {-3, 7, 11, 10, 4, 6};
		
		//testing partition
		System.out.println("PARTITION: ");
		int [] e_part = e;
		System.out.println("Middle - partition for E: " + part(e_part, 0, 5));
		System.out.println("E after partition: ");
		for (int i= 0; i<e_part.length; i++) {
			System.out.print(e_part[i]+ " ");
		}
		System.out.println();
		
		//testing select
		System.out.println("\nSELECT: ");
		int [] e_select = e;
		
		for (int i= 1; i<=e_select.length; i++) {
			System.out.println("#"+i +" smallest " + select(e_select, 6, i));
		}
		
		//testing median
		int [] e_median = e;
		System.out.println("\nMEDIAN: ");
		System.out.println("median of E: " + median(e_median));
		
		//testing quicksort
		int [] e_sort = e;
		System.out.println("\nQUICK SORT: ");
		quicksort(e_sort);
		for (int i= 0; i<e_sort.length; i++) {
			System.out.print(e_sort[i]+ " ");
		}
		System.out.println();
		
		
		/*F IS NOT SORTED, LARGEST NUMBER IS THE FIRST ONE*/
		System.out.println("\nF is not sorted array, where the first number is the largest: ");
		int [] f = {11, 7, -3, 10, 6};
		
		//testing partition
		System.out.println("PARTITION: ");
		int [] f_part = f;
		System.out.println("Middle - partition for F: " + part(f_part, 0, 4));
		System.out.println("F after partition: ");
		for (int i= 0; i<f_part.length; i++) {
			System.out.print(f_part[i]+ " ");
		}
		System.out.println();
		
		//testing select
		System.out.println("\nSELECT: ");
		int [] f_select = f;
		
		for (int i= 1; i<=f_select.length; i++) {
			System.out.println("#"+i +" smallest " + select(f_select, 5, i));
		}
		
		//testing median
		int [] f_median = f;
		System.out.println("\nMEDIAN: ");
		System.out.println("median of F: " + median(f_median));
		
		//testing quicksort
		int [] f_sort = f;
		System.out.println("\nQUICK SORT: ");
		quicksort(f_sort);
		for (int i= 0; i<f_sort.length; i++) {
			System.out.print(f_sort[i]+ " ");
		}
		System.out.println();
		
		
		/*G IS NOT SORTED, LARGEST NUMBER IS THE LAST ONE*/
		System.out.println("\nG is not sorted array where the last number is the largest: ");
		int [] g = { 7, -3, 10, 4, 6, 12};
		
		//testing partition
		System.out.println("PARTITION: ");
		int [] g_part = g;
		System.out.println("Middle - partition for G: " + part(g_part, 0, 5));
		System.out.println("G after partition: ");
		for (int i= 0; i<g_part.length; i++) {
			System.out.print(g_part[i]+ " ");
		}
		System.out.println();
		
		//testing select
		System.out.println("\nSELECT: ");
		int [] g_select = g;
		
		for (int i= 1; i<=g_select.length; i++) {
			System.out.println("#"+i +" smallest " + select(g_select, 6, i));
		}
		
		//testing median
		int [] g_median = g;
		System.out.println("\nMEDIAN: ");
		System.out.println("median of G: " + median(g_median));
		
		//testing quicksort
		int [] g_sort = g;
		System.out.println("\nQUICK SORT: ");
		quicksort(f_sort);
		for (int i= 0; i<g_sort.length; i++) {
			System.out.print(g_sort[i]+ " ");
		}
		System.out.println();
		
		/*H IS NOT SORTED, SMALLEST NUMBER IS THE LAST ONE*/
		System.out.println("\nH is not sorted array where the last number is the smallest: ");
		int [] h = { 7, 3, 10, 4, 6, -10};
		
		//testing partition
		System.out.println("PARTITION: ");
		int [] h_part = h;
		System.out.println("Middle - partition for H: " + part(h_part, 0, 5));
		System.out.println("H after partition: ");
		for (int i= 0; i<h_part.length; i++) {
			System.out.print(h_part[i]+ " ");
		}
		System.out.println();
		
		//testing select
		System.out.println("\nSELECT: ");
		int [] h_select = h;
		
		for (int i= 1; i<=h_select.length; i++) {
			System.out.println("#"+i +" smallest " + select(h_select, 6, i));
		}
		
		//testing median
		int [] h_median = h;
		System.out.println("\nMEDIAN: ");
		System.out.println("median of H: " + median(h_median));
		
		//testing quicksort
		int [] h_sort = h;
		System.out.println("\nQUICK SORT: ");
		quicksort(h_sort);
		for (int i= 0; i<h_sort.length; i++) {
			System.out.print(h_sort[i]+ " ");
		}
		System.out.println();
		
		/* J IS NOT SORTED, NO SAME ELEMENTS*/
		System.out.println("\nJ is not sorted array, none of the elements are the same: ");
		int [] j = { -3, 5, 6, -8, 10, 0, 4};
		
		//testing partition
		System.out.println("PARTITION: ");
		int [] j_part = j;
		System.out.println("Middle - partition for J: " + part(j_part, 0, 6));
		System.out.println("H after partition: ");
		for (int i= 0; i<j_part.length; i++) {
			System.out.print(j_part[i]+ " ");
		}
		System.out.println();
		
		//testing select
		System.out.println("\nSELECT: ");
		int [] j_select = j;
		
		for (int i= 1; i<=j_select.length; i++) {
			System.out.println("#"+i +" smallest " + select(j_select, 7, i));
		}
		
		//testing median
		int [] j_median = j;
		System.out.println("\nMEDIAN: ");
		System.out.println("median of J: " + median(j_median));
		
		//testing quicksort
		int [] j_sort = j;
		System.out.println("\nQUICK SORT: ");
		quicksort(j_sort);
		for (int i= 0; i<j_sort.length; i++) {
			System.out.print(j_sort[i]+ " ");
		}
		System.out.println();
		
		/*K IS NOT SORTED, HAVE SAME ELEMENTS*/
		System.out.println("\nK is not sorted array, some of the elements are the same: ");
		int [] k = { -3, 4, -3, -8, 10, 0, 4, 6, 4, 3};
		
		//testing partition
		System.out.println("PARTITION: ");
		int [] k_part = k;
		System.out.println("Middle - partition for K: " + part(k_part, 0, 9));
		System.out.println("K after partition: ");
		for (int i= 0; i<k_part.length; i++) {
			System.out.print(k_part[i]+ " ");
		}
		System.out.println();
		
		//testing select
		System.out.println("\nSELECT: ");
		int [] k_select = k;
		
		for (int i= 1; i<=k_select.length; i++) {
			System.out.println("#"+i +" smallest " + select(k_select, 10, i));
		}
		
		//testing median
		int [] k_median = k;
		System.out.println("\nMEDIAN: ");
		System.out.println("median of K: " + median(k_median));
		
		//testing quicksort
		int [] k_sort = k;
		System.out.println("\nQUICK SORT: ");
		quicksort(k_sort);
		for (int i= 0; i<k_sort.length; i++) {
			System.out.print(k_sort[i]+ " ");
		}
		System.out.println();
		
		/*P IS EMPTY*/
		System.out.println("\nP is an empty array ");
		int [] p = {};
		
		//testing partition
		System.out.println("PARTITION: ");
		int [] p_part = p;
		System.out.println("Middle - partition for P: " + part(p_part, 0, 0));
		System.out.println("P after partition: ");
		for (int i= 0; i<p_part.length; i++) {
			System.out.print(p_part[i]+ " ");
		}
		System.out.println();
		
		//testing select
		System.out.println("\nSELECT: ");
		int [] p_select = p;
		
		for (int i= 1; i<=p_select.length; i++) {
			System.out.println("#"+i +" smallest " + select(p_select, 10, 1));
		}
		
		//testing median
		int [] p_median = p;
		System.out.println("\nMEDIAN: ");
		System.out.println("median of P: " + median(p_median));
		
		//testing quicksort
		int [] p_sort = p;
		System.out.println("\nQUICK SORT: ");
		quicksort(p_sort);
		for (int i= 0; i<p_sort.length; i++) {
			System.out.print(p_sort[i]+ " ");
		}
		System.out.println();
		
		/*Q has one element*/
		System.out.println("\nQ is an array with one element ");
		int [] q = {3};
		
		//testing partition
		System.out.println("PARTITION: ");
		int [] q_part = q;
		System.out.println("Middle - partition for Q: " + part(q_part, 0, 0));
		System.out.println("Q after partition: ");
		for (int i= 0; i<q_part.length; i++) {
			System.out.print(q_part[i]+ " ");
		}
		System.out.println();
		
		//testing select
		System.out.println("\nSELECT: ");
		int [] q_select = q;
		
		for (int i= 1; i<=q_select.length; i++) {
			System.out.println("#"+i +" smallest " + select(q_select, 1, 1));
		}
		
		//testing median
		int [] q_median = q;
		System.out.println("\nMEDIAN: ");
		System.out.println("median of Q: " + median(q_median));
		
		//testing quick sort
		int [] q_sort = q;
		System.out.println("\nQUICK SORT: ");
		quicksort(q_sort);
		for (int i= 0; i<q_sort.length; i++) {
			System.out.print(q_sort[i]+ " ");
		}
		System.out.println();
		
		/*R has two elements, reversed*/
		System.out.println("\nR is a reverse ordered array with two elements ");
		int [] r = {3, 1};
		
		//testing partition
		System.out.println("PARTITION: ");
		int [] r_part = r;
		System.out.println("Middle - partition for R: " + part(r_part, 0, 1));
		System.out.println("R after partition: ");
		for (int i= 0; i<r_part.length; i++) {
			System.out.print(r_part[i]+ " ");
		}
		System.out.println();
		
		//testing select
		System.out.println("\nSELECT: ");
		int [] r_select = r;
		
		for (int i= 1; i<=r_select.length; i++) {
			System.out.println("#"+i +" smallest " + select(r_select, 2, i));
		}
		
		//testing median
		int [] r_median = r;
		System.out.println("\nMEDIAN: ");
		System.out.println("median of Q: " + median(r_median));
		
		//testing quicksort
		int [] r_sort = r;
		System.out.println("\nQUICK SORT: ");
		quicksort(r_sort);
		for (int i= 0; i<r_sort.length; i++) {
			System.out.print(r_sort[i]+ " ");
		}
		System.out.println();
		
		/* DUCH FLAG: RED = 0, WHITE = 1, BLUE = 2 */
		/* S is an unsorted array of 0, 1, and 2 */
		System.out.println("\nS is an unsorted array of 0, 1, and 2" );
		int [] s = {2, 0, 1, 1, 0, 0, 0, 2, 2, 2, 0, 1};
		
		int [] s_A = s;
		int [] s_B = s;
		
		invariantA(s_A);
		invariantB(s_B);
		
		System.out.println("invariantA for S: ");
		for (int i= 0; i<s_A.length; i++) {
			System.out.print(s_A[i]+ " ");
		}
		System.out.println();
		
		System.out.println("invariantB for S: ");
		for (int i= 0; i<s_B.length; i++) {
			System.out.print(s_B[i]+ " ");
		}
		System.out.println();
		
		/* T is a sorted array of 0, 1, and 2 */
		
		System.out.println("\nT is a sorted array of 0, 1, and 2" );
		int [] t = {0, 0, 0, 0, 1, 1, 1, 2, 2, 2, 2};
		
		int [] t_A = t;
		int [] t_B = t;
		
		invariantA(t_A);
		invariantB(t_B);
		
		System.out.println("invariantA for T: ");
		for (int i= 0; i<t_A.length; i++) {
			System.out.print(t_A[i]+ " ");
		}
		System.out.println();
		
		System.out.println("invariantB for T: ");
		for (int i= 0; i<t_B.length; i++) {
			System.out.print(t_B[i]+ " ");
		}
		System.out.println();
		}
}

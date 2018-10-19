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
		int l = left + 1;
		int r = right;
		int temp;
		
		//the loop runs until all the elements between right and left have been searched
		while (r - l > 1) {
		    //values that are unsorted in relation to a[left] are swapped
			while (a[r] > a[left] && r > left) {
				r--;
			}
			while (a[l] <= a[left] && l < right) {
				l++;
			}
			if (l < r){
				temp = a[l];
				a[l] = a[r];
				a[r] = temp;
			}
		}
		
		//code double checks that left was partitioned on correctly
		if (a[l] < a[left]) {
			temp = a[l];
			a[l] = a[left];
			a[left] = temp;}
		else if (l != 0){
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
			temp[i] = a[start];
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
			int m = part (a, 0, n - 1);
			//returns if the kth value is the middle result of part
			if(k == m + 1){
				return a[m];   
			}
			//case where the kth element is before middle value
			else if (k <= m) {
				a = subarray (a, 0, m );
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
	public static double median (int a[]) {
		int length = a.length;
		if (length % 2 == 1) {
			return (double) select (a, length, (length + 1) / 2);
		}
		else {
			int x = select (a, length, (length / 2) );
			System.out.println("x: " + x);
			int y = select (a, length, (length/ 2) + 1);
			System.out.println("y: " + y);
			return ((double) x + y)/2;
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
	public static void quicksortKernel (int a[], int left, int right) {
	    //quicksortKernel recursively uses partition to quicksort a
		int m = part(a, left, right);
		if(m > left + 1) {
			quicksortKernel (a, left, m - 1);
		}
		if (m < right - 1) {
			quicksortKernel(a, m + 1, right);
		}
	}

	/**
	 * quicksort sorts an array
	 * Preconditions:
	 *    @param a, an int array
	 * Postconditions:
	 *    a is sorted in ascending order
	 */
	public static void quicksort (int a[]) {
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
	public static void invariantA (int a[] ){
	    //positions of the segments initialized
		int red = 0;
		int white = 0;
		int blue = 0;
		int unsorted = 0;
		int temp;
		
		//loop finishes when unsorted is empty
		while(unsorted < a.length) {
		    //case in which a[unsorted] is blue
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
	public static void invariantB(int a []) {
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

	/**
	 * main tests the previous methods
	 * Preconditions:
	 *    none
	 * Postconditions:
	 *    previous methods are tested
	 */
	public static void main(String args []) {
		
		/*INVERSE, ODD LIST*/
		System.out.println("A is an inverse ordered, odd number lengthed array: ");
		int [] a = {9,8, 7, 6, 5};
		
		System.out.println("PARTITION: ");
		int [] a_part = a;
		System.out.println("Middle - partition for A: " + part(a_part, 0, 4));
		System.out.println("A after partition: ");
		for (int i= 0; i<a_part.length; i++) {
			System.out.print(a_part[i]+ " ");
		}
		System.out.println();
		
		System.out.println("\nSELECT: ");
		int [] a_select = a;
		
		for (int i= 1; i<=a_select.length; i++) {
			System.out.println("#"+i +" smallest " + select(a_select, 5, i));
		}
		
		int [] a_median = a;
		System.out.println("\nMEDIAN: ");
		System.out.println("median of A: " + median(a_median));
		
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
		
		System.out.println("PARTITION: ");
		int [] b_part = b;
		System.out.println("Middle - partition for B: " + part(b_part, 0, 3));
		System.out.println("B after partition: ");
		for (int i= 0; i<b_part.length; i++) {
			System.out.print(b_part[i]+ " ");
		}
		System.out.println();
		
		System.out.println("\nSELECT: ");
		int [] b_select = b;
		
		for (int i= 1; i<=b_select.length; i++) {
			System.out.println("#"+i +" smallest " + select(b_select, 4, i));
		}
		
		int [] b_median = b;
		System.out.println("\nMEDIAN: ");
		System.out.println("median of B: " + median(b_median));
		
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
		
		System.out.println("PARTITION: ");
		int [] c_part = c;
		System.out.println("Middle - partition for C: " + part(c_part, 0, 4));
		System.out.println("C after partition: ");
		for (int i= 0; i<c_part.length; i++) {
			System.out.print(c_part[i]+ " ");
		}
		System.out.println();
		
		System.out.println("\nSELECT: ");
		int [] c_select = c;
		
		for (int i= 1; i<=c_select.length; i++) {
			System.out.println("#"+i +" smallest " + select(c_select, 5, i));
		}
		
		int [] c_median = c;
		System.out.println("\nMEDIAN: ");
		System.out.println("median of C: " + median(c_median));
		
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
		
		System.out.println("PARTITION: ");
		int [] d_part = d;
		System.out.println("Middle - partition for D: " + part(d_part, 0, 3));
		System.out.println("D after partition: ");
		for (int i= 0; i<d_part.length; i++) {
			System.out.print(d_part[i]+ " ");
		}
		System.out.println();
		
		System.out.println("\nSELECT: ");
		int [] d_select = d;
		
		for (int i= 1; i<=d_select.length; i++) {
			System.out.println("#"+i +" smallest " + select(d_select, 4, i));
		}
		
		int [] d_median = d;
		System.out.println("\nMEDIAN: ");
		System.out.println("median of D: " + median(d_median));
		
		int [] d_sort = d;
		System.out.println("\nQUICK SORT: ");
		quicksort(d_sort);
		for (int i= 0; i<d_sort.length; i++) {
			System.out.print(d_sort[i]+ " ");
		}
		System.out.println();
	}

}

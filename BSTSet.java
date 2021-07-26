/*******************************************************************************
* Hamdan Basharat / basham1 / 400124515
* BSTSet Class 
*******************************************************************************/
package TestBSTSet;
import java.util.Stack; //imports Stack class to use in non-recursive methods

public class BSTSet {
    private TNode root; //reference to the root of the tree
    
    /***************************************************************************
    * Constructor: initializes the BSTSet object to represent the empty set.
    * @param: none
    ***************************************************************************/
    public BSTSet(){root=null;} //if the root is null the set is empty
    /***************************************************************************
    * Constructor: initializes the BSTSet object to represent the set containing
    * all elements in array input, without repetitions.
    * This creates a balanced binary search tree which makes a tree of minimum 
    * height, satisfying the bonus requirement. 
    * @param: type integer []
    ***************************************************************************/
    public BSTSet(int[] input){
        if(input.length==0){this.root=null;} //if the array has length of zero, the set is empty
        else{
            input = sort(input); //calls sort to sort the inputted array
            BSTSet(input,0,input.length-1); //calls the actual constructor
        }
    }
    /***************************************************************************
    * Constructor: Performs array to binary search tree conversion.
    * @param: type integer [], integer, integer @return: type TNode
    ***************************************************************************/
    private TNode BSTSet(int[] input,int first,int last){
        if(first>last){return null;} //the base case
        TNode n = root;
        int mid=(first+last)/2; //find the element in the middle
        TNode temp = new TNode(input[mid],null,null); //the middle element becomes the root
        temp.left=BSTSet(input,first,mid-1); //uses recursion to construct the nodes on thee left and right
        temp.right=BSTSet(input,mid+1,last);
        root=temp; //reassigns the root
        return root;
    }
    
    /***************************************************************************
    * Method: Sorts the array in ascending order and removes any numbers that
    * appear more than once.
    * @param: type integer[] @return: type integer[]
    ***************************************************************************/
    public int[] sort(int input[]){   
        for(int i=0;i<input.length-1;i++){ 
            int min=i; //sets the current value as the minimum
            for(int j=i+1;j<input.length;j++) 
                if(input[j]<input[min]){min=j;}//checks if the current index of the array is smaller than the minimum, then assigns it accordingly
            int temp=input[min]; 
            input[min]=input[i];
            input[i]=temp; //reorders the array from smallest to largest
        }
        int n=0;
        int[] copy = new int[input.length]; //creates a new array with the same size as the original
        for(int k=0;k<input.length-1;k++)
            if(input[k]!=input[k+1]){copy[n++]=input[k];} //if the index has the same value as the next index, shifts all the values to the left
        copy[n++]=input[input.length-1];
        int[] unique = new int[n]; //creates a new array with size the same as the number of indexes with values
        for(int m=0;m<n;m++){unique[m]=copy[m];} //fills the array so as to not have any empty indexes
        return unique;
    }
    /***************************************************************************
    * Method: Non-recursively converts the set back to an integer array.
    * @param: type TNode, integer[] @return: type integer[]
    ***************************************************************************/
    public int[] toArray(TNode input,int[] results){
        int i=0;
        Stack<TNode> stack=new Stack<TNode>(); //creates a new stack object
        while(input!=null||stack.size()>0){ //runs as long as the node has a value and the stack has something on it
            while(input!=null){ //runs as long as the node has a value
                stack.push(input); //pushes the node onto the stack
                input=input.left; //assigns the current node to the left child
            }
            input=stack.pop(); //pops the node off the stack
            results[i++]=input.element; //assigns the value of the node to the array index
            input=input.right; //assigns the current node to the right child
        }
        return results;
    }
    /***************************************************************************
    * Method: Returns true if integer v is an element of this BSTSet. It returns
    * false otherwise.
    * @param: type integer @return: type boolean
    ***************************************************************************/
    public boolean isIn(int v){
        System.out.print(v+" is in the set: ");        
        return(this.isIn(root,v)); //returns whether the value is in the set or not    
    }
    private boolean isIn(TNode input,int v){
        if(input==null){return false;} //if the node is null, returns false
        if(input.element>v){return isIn(input.left, v);} //if the node is larger than the value, traverses left
        if(input.element<v){return isIn(input.right, v);} //if the node is smaller than the value, traverses right
        else{return true;} //if the value is found, returns true
    }
    /***************************************************************************
    * Method: Adds v to this BSTSet if v was not already an element of this 
    * BSTSet. It does nothing otherwise.
    * @param: type integer @return: none 
    ***************************************************************************/
    public void add(int v){
        root=this.add(root,v); //calls the add method and assigns its value to root
    }
    private TNode add(TNode input,int v){
        if(input==null){
            input=new TNode(v,null,null); //if the node is null, creates a new (sub)tree with the inputted value as the first node
            return input;
        }
        //assigns the new  value to the correct position
        if(v<input.element){input.left=add(input.left,v);} //if the node is larger than the value, traverses left
        if(v>input.element){input.right=add(input.right,v);} //if the node is smaller than the value, traverses right
        return input;
    }
    /***************************************************************************
    * Method: Removes v from this BSTSet if v was an element of this BSTSet and 
    * returns true. Returns false if v was not an element of this BSTSet.
    * @param: type integer @return: type boolean
    ***************************************************************************/
    public boolean remove(int v){
        int before=this.size(); //finds the size before removing the node
        this.remove(root,v); //calls the method to remove the node
        int after=this.size(); //finds the size after removing the node
        return (before>after)? true : false; //returns true if the size decreased
    }
    private TNode min(TNode input){
        if(input.left==null){return input;} //finds the smallest value in the set
        return min(input.left); 
    }
    private TNode remove(TNode input,int v){
        if(input==null){return input;} //if the set is empty, returns the inputted array
        //recursively calls the method till it finds the right position
        if(input.element>v){input.left=remove(input.left,v);} //if the node is larger than the value, traverses left
        else if(input.element<v){input.right=remove(input.right,v);} //if the node is smaller than the value, traverses right
        else{
            if(input.left==null){return input.right;} //if left child is empty, returns the right child
            else if(input.right==null){return input.left;} //if the right childs is empty, returns the left child
            input=min(input.right);
            input.right=remove(input.right,input.element); //assigns the right child to the value returned from the remove method
        }
        return input;
    }
    /***************************************************************************
    * Method: Returns a new BSTSet which represents the union of this BSTSet and
    * s. This method should not modify the input sets.
    * @param: type BSTSet @return: type BSTSet
    ***************************************************************************/
    public BSTSet union(BSTSet s){
        int[] results1 = new int[size(this.root)]; //creates 3 arrays to hold the values of the sets
        int[] results2 = new int[size(s.root)];
        int[] results3 = new int[size(this.root)+size(s.root)];
        results1=toArray(this.root,results1); //converts the sets back to the arrays
        results2=toArray(s.root,results2);
        for(int j=0;j<results1.length;j++){ 
            results3[j]=results1[j]; //adds the values from the first array to the final array
        }
        int k=0;
        for(int j=results1.length;j<(results1.length+results2.length);j++){
            results3[j]=results2[k]; //adds the values from the first array to the final array
            k++;
        }
        return new BSTSet(results3); //creates and returns a new set representing the union of the two inputs
    }
    /***************************************************************************
    * Method: Returns a new BSTSet which represents the the intersection of this
    * BSTSet and s. This method should not modify the input sets.
    * @param: type BSTSet @return: type BSTSet
    ***************************************************************************/
    public BSTSet intersection(BSTSet s){
        int[] results1 = new int[size(this.root)]; //creates 3 arrays to hold the values of the sets
        int[] results2 = new int[size(s.root)];
        int larger = size(this.root)>size(s.root) ? size(this.root) : size(s.root); //finds the larger of the two sizes
        int[] results3 = new int[larger];
        results1=toArray(this.root,results1); //converts the sets back to the arrays
        results2=toArray(s.root,results2);
        int f=0;
        for(int j=0;j<results1.length;j++){
            for(int l=0;l<results2.length;l++){ //scans through the second array
                if(results1[j]==results2[l]){ //checks if the value of the index in the first array matches any index in the second array
                    results3[f]=results1[j]; //adds the values from the first array to the final array
                    f++;
                }
            }
        }
        int[] results4 = new int[f]; //creates a new array and fills it to get rid of the empty spaces in the old array
        for(int i=0;i<f;i++){results4[i]=results3[i];}
        return new BSTSet(results4); //creates and returns a new set representing the intersection of the two inputs
    }
    /***************************************************************************
    * Method: Returns a new BSTSet which represents the the difference of this
    * BSTSet and s. This method should not modify the input sets.
    * @param: type BSTSet @return: type BSTSet
    ***************************************************************************/
    public BSTSet difference(BSTSet s){
        int[] results1 = new int[size(this.root)]; //creates 3 arrays to hold the values of the sets
        int[] results2 = new int[size(s.root)];
        int[] results3 = new int[size(this.root)];
        results1=toArray(this.root,results1); //converts the sets back to the arrays
        results2=toArray(s.root,results2);
        int f=0,flag=0;
        for(int j=0;j<results1.length;j++){
            for(int l=0;l<results2.length;l++) //scans through the second array
                if(results1[j]==results2[l]){flag=1;} //checks if the value of the index in the first array matches any index in the second array and turns the flag true 
            if(flag!=1){results3[f++]=results1[j];} //adds the values from the first array to the final array if the flag isnt true
            flag=0; //resets the flag to false
        }
        int[] results4 = new int[f]; //creates a new array and fills it to get rid of the empty spaces in the old array
        for(int i=0;i<f;i++){results4[i]=results3[i];}
        return new BSTSet(results4); //creates and returns a new set representing the intersection of the two inputs
    }
    /***************************************************************************
    * Method: Returns the number of elements in this set.
    * @param: none @return: type integer
    ***************************************************************************/
    public int size(){
        return size(root); //returns the size of the tree
    }
    private int size(TNode input){
        if(input==null){return 0;} //if the set is empty the size is zero
        else{return size(input.left)+size(input.right)+1;} //recursively calls the left and right halves of the tree and adds the values
    }
    /***************************************************************************
    * Method: Returns the height of this BSTSet. Height of empty set is -1
    * @param: none @return: type integer
    ***************************************************************************/
    public int height(){
        System.out.print("The height of the set is: ");
        return height(root); //returns the height of the tree
    }
    private int height(TNode input){
        if(input==null){return -1;} //if the set is empty, the height is -1
        int heightL=height(input.left); //recursively calls the left children to find the height
        int heightR=height(input.right); //recursively calls the right children to find the height
        return (heightL>heightR) ? (heightL+1) : (heightR+1); //returns the larger of the two heights
    }
    /***************************************************************************
    * Method: Outputs the elements of this set to the console, in increasing 
    * order
    * @param: none  @return: none 
    ***************************************************************************/
    public void printBSTSet(){
        if(root==null) //if the root is null, the set is empty
            System.out.println("The set is empty");
        else{
            System.out.print("The set elements are: ");
            printBSTSet(root); //calls the print method 
            System.out.print("\n");
        }
    }
    /***************************************************************************
    * Method: Outputs to the console the elements stored in the subtree rooted 
    * in t, in increasing order
    * @param: type TNode  @return: none
    ***************************************************************************/
    private void printBSTSet(TNode t){
        if(t!=null){ //runs as long as the node has a value
            printBSTSet(t.left); //recursively calls the left children
            System.out.print(" " + t.element + ", ");
            printBSTSet(t.right); //recursively calls the right children
        }
    }
    /***************************************************************************
    * Method: Prints the integers in this BSTSet in increasing order. This method
    * is nonrecursive and uses stack to implement the in-order traversal
    * @param: none  @return: none
    ***************************************************************************/
    public void printNonRec(){
        if(root==null){ //if the root is null, the set is empty
            System.out.println("The set is empty");
        }
        else{
            System.out.print("The set elements are: ");
            printNonRec(root); //calls the non-recursive print method
            System.out.print("\n");
        }
    }
    private void printNonRec(TNode t){
        Stack<TNode> stack=new Stack<TNode>(); //creates a new stack object
        while(t!=null||stack.size()>0){ //runs as long as the node has a value and the stack has something on it
            while(t!=null){ //runs as long as the node has a value
                stack.push(t); //pushes the node onto the stack
                t=t.left; //assigns the current node to the left child
            }
            t=stack.pop(); //pops the node off the stack
            System.out.print(" " + t.element + ", "); //prints the element
            t=t.right; //assigns the current node to the right child
        }
    }
}
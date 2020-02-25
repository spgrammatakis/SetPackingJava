class ItemNode {

	public int item;
	public ItemNode next;

	public ItemNode(int item) {
		this.item = item;
		this.next = null;
	}
}

class ItemsList {
	private int nbNodes;
	private ItemNode first;
	private ItemNode last;

	public ItemsList() {
		this.first = null;
		this.last = null;
		this.nbNodes = 0;
	}

	public int size() { return nbNodes; }

	public boolean empty() { return first == null; }
        // true εάν η λίστα που καλείτε η contains εμπεριέχει την ilist
	public boolean contains(ItemsList ilist) {
            // εάν η ilist είναι άδεια return false
		if(ilist.empty()){
			return false;
		}
            // εάν η λίστα στην οποία καλείται η cotains είναι άδεια return false    
		if(empty()){
			return false;
		}
                
		ItemNode ilistCurrent = ilist.first;
		ItemNode thisCurrent  = first;
            // ελέγχω τις 2 λίστες για κοινούς κόμβους  
		while(thisCurrent != null){
			while(ilistCurrent != null){
				if(thisCurrent == null){
					return false;
				}else if(ilistCurrent.item == thisCurrent.item){
					ilistCurrent = ilistCurrent.next;
					thisCurrent  = thisCurrent.next;
				}else break;
			}
                        // εάν διασχίσει όλους τους κόμβους της ilist τότε 
                        // σημαίνει ότι εμπεριέχεται η ilist στην λίστα που
                        // καλείται η contains
			if(ilistCurrent == null){
				return true;
			}
			ilistCurrent = ilist.first;
			thisCurrent = thisCurrent.next;
		}
		return false;
	}
        // δημιουργεί κόμβο κλάσης ItemNode, στον οποίο γράφει item 
        // και επισυνάπτει τον κόμβο στο τέλος της λίστα που καλείται η μέθοδος
        // επιστρέφει το ενημερωμένο πλήθος κόμβων της λίστας
	public int append(int item) {		 
		ItemNode newNode = new ItemNode(item);
                if(first == null){
                first = last = newNode;
                newNode = null;
                return nbNodes++;
                }else{
                    last.next = newNode;
                    last = newNode;
                    return nbNodes++;
                }
	}
        // διαγράφει από τη λίστα που καλείται ( remove.list) όλους τους κόμβους
        // που περιέχουν κλειδιά/στοιχεία των κόμβων της ilist
	public void remove(ItemsList ilist) 
        {
		 
		if(ilist.empty()){return;}
		if(empty()){return;}

		ItemNode ilistCurrent = ilist.first;
		ItemNode thisCurrent = first;
		ItemNode previous = null;
		while(ilistCurrent !=null){
			int key = ilistCurrent.item;
			while(thisCurrent != null){
				if(key == thisCurrent.item){
					if(thisCurrent == first){
						first = first.next;
					}else if(thisCurrent == last){
						previous.next = null;
						last = previous;
					}else{
						previous.next = thisCurrent.next;
					}
					break;
				}
				previous = thisCurrent;
				thisCurrent = thisCurrent.next;
			}
			ilistCurrent = ilistCurrent.next;
			previous = null;
			thisCurrent = first;
		}
	}

}//end of itemlist

class BuyerNode {

	public int id;
	public int value;
	public ItemsList itemsList;
	public BuyerNode next;


	public BuyerNode(int id, int value, ItemsList ilist) {
		this.id = id;
		this.value = value;
		this.itemsList = ilist;
	}
}

class BuyersList {
	public int opt;
	private int nbNodes;
	private BuyerNode first;
	private BuyerNode last;

	public BuyersList() {
		this.first = null;
		this.last = null;
		this.nbNodes = 0;
	}

	public int readFile(String filename) {

		int m = 0;
		java.io.BufferedReader br = null;

		try {
			br = new java.io.BufferedReader(new java.io.FileReader(filename));

			// Read dimensions
			String line = br.readLine();
			String[] data = line.split(" ");
			// Number of items
			m = Integer.parseInt(data[0]);
			// Number of buyers
			int n = Integer.parseInt(data[1]);
			// Optimum revenue
			this.opt = Integer.parseInt(data[2]);

			// Read Buyers Information
			int id = 0;
			while((line = br.readLine()) != null) {
				data = line.split(" ");
				// Read value
				int value = Integer.parseInt(data[0]);
				// Read Item List
				ItemsList itemsList = new ItemsList();
				for(int i = 1; i < data.length; i++)
					itemsList.append(Integer.parseInt(data[i]));
				// Insert new buyer
				this.append(id++, value, itemsList);
			}
		} catch(java.io.IOException e) {
			e.printStackTrace();
		} finally {
			try { if (br != null) br.close(); }
			catch (java.io.IOException ex) { ex.printStackTrace(); }
		}
		return m;
	}

	public boolean empty() { return first == null; }

	public int size() { return nbNodes; }
        // υπολογίζει και επιστρέφει την ολική αξία των αγοραστών
        // της λίστας BuyersList που καλείται η μέθοδος
	public int totalValue() {
		 
		BuyerNode newNode = first;
		int value = 0;
		while( newNode != null)
		{
		value += newNode.value;
		newNode = newNode.next;
		}
        return value;
	}
        // δημιουργία κόμβου BuyerNode αποθηκεύει
        // id,value,ilist(λίστα ενδιαφέροντος)
        // επισυνάπτει το node στο τέλος της λίστας που καλείται η μέθοδος
        // επιστρέφει τρέχον μήκος λίστας κατόπιν επισύναψης
	public int append(int id, int value, ItemsList ilist) {
		BuyerNode newNode = new BuyerNode(id,value,ilist);
                if(first == null){
                first = last = newNode;
                newNode = null;
                return nbNodes++;
                }else{
                    last.next = newNode;
                    last = newNode;
                    return nbNodes++;
                }
	}
	
        // άπληστος αλγόριθμος
        // επιστρέφει λίστα κλάσης BuyersList με το υποσύνολο των επιλεγμένων αγραστών
	public BuyersList greedy(int m) {
		 
		if(empty()){
			return null;
		}
		BuyersList result = new BuyersList();
		ItemsList  items  = new ItemsList();
                // συνδεδεμένη λίστα με δείκτες αντικειμένων
		for(int i=1; i<=m;i++){
			items.append(i);
		}
		float max;
		int currentID = first.id;
                //items.size είναι ακέραιος όμως το max μπορεί να είναι δεκαδικός
                // οπότε χρησιμοποιούμε το (float)
		max = first.value/(float)items.size();               
		BuyerNode currentNode = first;
		while(!empty()){
			while(currentNode != null){
                            // ενημέρωση max εάν βρεθεί μεγαλύτερο πηλίκο
				if(max<currentNode.value/(float)items.size()){
					max = currentNode.value/items.size();
					currentID = currentNode.id;
				}
			currentNode = currentNode.next;
			}
			currentNode = first;
			BuyerNode previousNode = null;

			while(currentNode.id != currentID){
				previousNode = currentNode;
				currentNode = currentNode.next;
			}
                        // ελέγχω εάν τα προϊόντα που θέλει ο αγοραστης είναι διαθέσιμα
			if(items.contains(currentNode.itemsList)){
			result.append(currentNode.id,currentNode.value,currentNode.itemsList);
                        //αφαιρώ τα αντικείμενα που ήθελε ο επιλαχών αγοραστής
			items.remove(currentNode.itemsList);
			}
			if(currentNode == first){
				first = first.next;
			}else if(currentNode == last){
				previousNode.next = null;
				last = previousNode;
			}else{
				previousNode.next = currentNode.next;
			}
                        currentNode = first;

		}
		return result;
	}
        // πειραματισμός
        // τουλάχιστον 10 φορές εκτέλεση για κάθε αρχείο
        // μέσος χρόνος εκτέλεσης του greedy
        // αξία λύσης, αξία βέλτισης λύσης(opt)
        private static void output(String filePath){
 		BuyersList list = new BuyersList(); 
                int i = 0;
		int m = 0;  
                int value = 0;
                BuyersList listFinal = null;
		int optValue = 0;
		long start = System.currentTimeMillis();
                // τρέχω 10 φορές για κάθε αρχείο τον αλγόριθμο 
                // διαιρώ με το 10 στο output για να βρω avgTime
                while(i < 10){
                m = list.readFile(filePath);
 		listFinal = list.greedy(m); 
                value = listFinal.totalValue();
                i++;
                }
                long end = System.currentTimeMillis();
                optValue = list.opt;
                // έλεγχος άμα το String filePath εμπεριέχει το substring ./m500    
                if(filePath.contains("./m500")){
                // εάν το περιέχει μετά αναλόγως το αρχείο εμφανίζει στην οθόνη
                // το αντίστοιχο μήνυμα
                    if(filePath.contains("x1000.txt")){
                    System.out.println("*m = 500\n -n = 1000 avgTime = " + (end-start)/10 + " greedy value = " + value + " opt value = " + optValue );
                }else if(filePath.contains("x3000.txt")){
                    System.out.println("*m = 500\n -n = 3000 avgTime = " + (end-start)/10 + " greedy value = " + value + " opt value = " + optValue );
                }else if(filePath.contains("x5000.txt")){
                    System.out.println("*m = 500\n -n = 5000 avgTime = " + (end-start)/10 + " greedy value = " + value + " opt value = " + optValue );
                }else if(filePath.contains("x7000.txt")){
                    System.out.println("*m = 500\n -n = 7000 avgTime = " + (end-start)/10 + " greedy value = " + value + " opt value = " + optValue );
                }else{
                    System.out.println("*m = 500\n -n = 9000 avgTime = " + (end-start)/10 + " greedy value = " + value + " opt value = " + optValue );}
                }
                
                // ομοίως με το ./m500. Ελέγχει εάν υπάρχει το substring και εμφανίζει
                // το αντίστοιχο μήνυμα στην οθόνη
                if(filePath.contains("./n2000")){
                    if(filePath.contains("1000x")){
                    System.out.println("*n = 2000\n m - = 1000 avgTime = " + (end-start)/10 + " greedy value = " + value + " opt value = " + optValue );
                }else if(filePath.contains("200x")){
                    System.out.println("*n = 2000\n -m = 200 avgTime = " + (end-start)/10 + " greedy value = " + value + " opt value = " + optValue );
                }else if(filePath.contains("400x")){
                    System.out.println("*n = 2000\n -m = 400 avgTime = " + (end-start)/10 + " greedy value = " + value + " opt value = " + optValue );
                }else if(filePath.contains("600x")){
                    System.out.println("*n = 2000\n -m = 600 avgTime = " + (end-start)/10 + " greedy value = " + value + " opt value = " + optValue );
                }else{
                    System.out.println("*n = 2000\n -m = 800 avgTime = " + (end-start)/10 + " greedy value = " + value + " opt value = " + optValue );}
                }                
              
 		
        }
	public static void main(String[] args) {
                int fileIndex = 1000;
                String filePath = " ";
                // στις οδηγίες είχατε αναφέρει ότι δεν μπορούμε να χρησιμοποιήσουμε
                // δομές βιβλιοθήκης της java. Βρήκα τον τρόπο να διαβάζει τα αρχεία
                // δημιουργώντας αρχείο τύπου File και μετά μέσω της μεθόδου .walk
                // αλλά επειδή χρειάζεται να γίνει import extra library(java.nio.file.Files;)
                // θεώρησα ότι παραβιάζει τους κανόνες. 

                while(fileIndex < 9001){
                filePath = "./m500/p500x" + fileIndex + ".txt";
                output(filePath);
                fileIndex += 2000;
                }
                fileIndex = 200;
                while(fileIndex < 1001){
                filePath = "./n2000/p" + fileIndex + "x2000.txt";
                output(filePath);
                fileIndex += 200;
                }
	}
}
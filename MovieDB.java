import java.util.Iterator;


/**
 * Genre, Title 을 관리하는 영화 데이터베이스.
 * 
 * MyLinkedList 를 사용해 각각 Genre와 Title에 따라 내부적으로 정렬된 상태를  
 * 유지하는 데이터베이스이다. 
 */

public class MovieDB {
	private MyLinkedList<MyLinkedList<String>> Genrelist;
	
    public MovieDB() {
    	Genrelist = new MyLinkedList<MyLinkedList<String>>();
    }

    public void insert(MovieDBItem item) {
    	MyLinkedListIterator<MyLinkedList<String>> iterator_gen = Genrelist.iterator();
    	
    	MyLinkedList<String> search_gen = new MyLinkedList<String>();
        int flag = 0;
    	while(iterator_gen.hasNext()){
        	search_gen = iterator_gen.next();
        	
        	switch(Compare(search_gen.head.getItem(),item.getGenre())){
        	case 0:
        		flag =0;
        		MyLinkedListIterator<String> iterator_tit = search_gen.iterator();
        		String search_tit;
        		int flag2 = 0;
        		while(iterator_tit.hasNext()){
        			search_tit = iterator_tit.next();
        			switch(Compare(search_tit, item.getTitle())){
        			case 0:
        				return;
        			case 1:
        				flag2 = 1; break;
        			case 2:
        				flag2 = 2; break;
        			}
        			
        			if(flag2 == 1){ break; }
        		}
        		if(flag2 == 1){
        			iterator_tit.add(item.getTitle());
        		}else{
        			search_gen.add(item.getTitle());
        		}
        		return;
        	case 1:
        		flag = 1; break; //insert between prev & curr
        	case 2:
        		flag = 2; break; //keep go on
        	}
        	if(flag == 1){ break; }
        }
    	
    	MyLinkedList<String> temp = new MyLinkedList<String>(item.getGenre());
		temp.add(item.getTitle());
		
    	if(flag == 1){
    		iterator_gen.add(temp);
    	}else{
    		Genrelist.add(temp);
    	}
 
    }
    
    // returns s1 = s2 => 0
    // s1 < s2 => 2
    // s1 > s2 => 1
    private static int Compare(String s1, String s2){
    	if( s1.equals(s2)){
    		return 0;
    	}else{
    		int i=0;
    		while(i< s1.length() && i< s2.length()){
    			if(s1.charAt(i)< s2.charAt(i)){
    				return 2;
    			}else if(s1.charAt(i) > s2.charAt(i)){
    				return 1;
    			}else{
    				i++;
    			}
    		}
    		if(s1.length()>s2.length()){
    			return 1;
    		}else{
    			return 2;
    		}
    	}
    }
    
    public void delete(MovieDBItem item) {
        MyLinkedListIterator<MyLinkedList<String>> iterate_gen = Genrelist.iterator();
    	MyLinkedListIterator<String> iterate_tit;
    	   	
    	MyLinkedList<String> temp = new MyLinkedList<String>();
    	
    	while(iterate_gen.hasNext()){
    		temp = iterate_gen.next();
    		if(temp.head.getItem().equals(item.getGenre())) {
    			break;
    		}
    	}
    	    	    	
    	iterate_tit = temp.iterator();    	
    	
    	while(iterate_tit.hasNext()){
    		if(iterate_tit.next().equals(item.getTitle()) && temp.head.getItem().equals(item.getGenre())){
    			if(temp.numItems == 1){
    				iterate_gen.remove();
    			}else{
    				iterate_tit.remove();
    			}
    			return;
    		}
    	}
    	
    }

    public MyLinkedList<MovieDBItem> search(String term) {
    	MyLinkedListIterator<MyLinkedList<String>> iterate_gen = Genrelist.iterator();
    	MyLinkedListIterator<String> iterate_tit;
    	
    	MyLinkedList<MovieDBItem> results = new MyLinkedList<MovieDBItem>(); 
    	
    	MyLinkedList<String> temp = new MyLinkedList<String>();
    	String temp2;
    	
    	while(iterate_gen.hasNext()){
    		temp = iterate_gen.next();
    		iterate_tit = temp.iterator();
    		
    		while(iterate_tit.hasNext()){
    			temp2 = iterate_tit.next();
    			if(temp2.contains(term)){
    				MovieDBItem addnode = new MovieDBItem(temp.head.getItem(),temp2);
    				results.add(addnode);
    			}
    		}
    	}
    	
    	        return results;
    }
    
    public MyLinkedList<MovieDBItem> items() {
            	   
        MyLinkedListIterator<MyLinkedList<String>> iterate_gen = Genrelist.iterator();
    	MyLinkedListIterator<String> iterate_tit;
    	
    	MyLinkedList<MovieDBItem> results = new MyLinkedList<MovieDBItem>(); 
    	
    	MyLinkedList<String> temp = new MyLinkedList<String>();
    	String temp2;
    	
    	while(iterate_gen.hasNext()){
    		temp = iterate_gen.next();
    		iterate_tit = temp.iterator();
    		
    		while(iterate_tit.hasNext()){
    			temp2 = iterate_tit.next();	
    			MovieDBItem addnode = new MovieDBItem(temp.head.getItem(),temp2);
    			results.add(addnode);
    		}
    	}
        
    	return results;
    }
}


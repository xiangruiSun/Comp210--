package assn07;

import java.util.*;

public class PasswordManager<K,V> implements Map<K,V> {
    private static final String MASTER_PASSWORD = "password321";
    private Account[] _passwords;

    public PasswordManager() {
        _passwords = new Account[50];
    }

    // TODO: put
    @Override
    public void put(K key, V value) {
        int index = Math.abs(key.hashCode()) % 50;
        Account<K,V> tail = new Account<>(key,value);
        Account<K,V> node = _passwords[index];
        ///base case
        if (_passwords[index] == null){
            _passwords[index] = tail;
            return;
        }

        if (node.getWebsite().equals(key)){
            node.setPassword(value);
            return;
        }

        ///chain
        while(node.getNext() != null){
            node = node.getNext();
            if (node.getWebsite().equals(key)){
                node.setPassword(value);
                return;
            }
        }

        node.setNext(tail);
        return;
    }

    // TODO: get
    @Override
    public V get(K key) {
        int index = Math.abs(key.hashCode()) % 50;
        if(_passwords[index] == null){
            return null;
        }

        else{
            Account<K,V> node = _passwords[index];
            if (node.getWebsite().equals(key)){
                return node.getPassword();
            }

            while(node.getNext() != null){
                node = node.getNext();
                if (node.getWebsite().equals(key)){
                    return node.getPassword();
                }
            }
            return null;
        }
    }

    // TODO: size
    @Override
    public int size() {
        int size = 0;
        for (int i = 0; i<50; i++){
            if (_passwords[i] != null){
                size += 1;
                Account<K,V> node = _passwords[i];
                while(node.getNext() != null){
                    size += 1;
                    node = node.getNext();
                }
            }
        }
        return size;
    }

    // TODO: keySet
    @Override
    public Set<K> keySet() {
        Set<K> keys = new HashSet<>();
        for (int i = 0; i<50; i++){
            if (_passwords[i] != null){
                Account<K,V> node = _passwords[i];
                keys.add(node.getWebsite());
                while(node.getNext() != null){
                    node = node.getNext();
                    keys.add(node.getWebsite());
                }
            }
        }
        return keys;
    }

    // TODO: remove
    @Override
    public V remove(K key) {
        int index = Math.abs(key.hashCode()) % 50;
        if(_passwords[index] == null){
            return null;
        }

        else{
            Account<K,V> node = _passwords[index];
            if (node.getWebsite().equals(key)){
                V password = node.getPassword();
                _passwords[index] = _passwords[index].getNext();
                return password;
            }

            while(node.getNext() != null){
                Account<K,V> p = node;
                node = node.getNext();
                if (node.getWebsite().equals(key)){
                    V password = node.getPassword();
                    p.setNext(node.getNext());
                    return password;
                }
            }
            return null;
        }

    }

    // TODO: checkDuplicate
    @Override
    public List<K> checkDuplicate(V value) {
        List<K> rList = new ArrayList<>();
        for (int i = 0; i<50; i++){
            if (_passwords[i]!=null){
                Account<K,V> node = _passwords[i];
                if (node.getPassword().equals(value)){
                    rList.add(node.getWebsite());
                }

                while(node.getNext() != null){
                    node = node.getNext();
                    if (node.getPassword().equals(value)){
                        rList.add(node.getWebsite());
                    }
                }
            }
        }
        return rList;
    }


    // TODO: checkMasterPassword
    @Override
    public boolean checkMasterPassword(String enteredPassword) {
        return enteredPassword.equals(MASTER_PASSWORD);
    }

    @Override
    public String generatesafeRandomPassword(int length) {
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = length;
        Random random = new Random();

        // TODO: Ensure the minimum length is 4
        if (length < 4){
            length = 4;
        }

        String generatedString = random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();

        return generatedString;
    }

    /*
    Used for testing, do not change
     */
    public Account[] getPasswords() {
        return _passwords;
    }
}

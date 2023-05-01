package HackerRankPraktikum.TreeMekanikDirSys;

import java.util.ArrayList;
import java.util.List;
import java.util.*;


//pointer selalu berada di root
//pointer hanya menunujuk folder bukan file
//Ada 7 operasi/perintah ;
// 1. mkdir (nama-folder)
//--> Akan membuat folder baru (make directory) pada lokasi pointer
//2. touch (nama-file)
//--> Akan membuat file baru pada lokasi pointer
//3. cd (target-folder)
//--> Memindahkan pointer ke (target-folder). (target-folder) merupakan folder didalam pointer pada saat cd dieksekusi. jika (target-folder) adalah .. maka pointer berpindah ke parent dari node sekarang
//4. ls
//--> Mencetak semua file dan folder di posisi pointer sekarang
//5. rm (target)
//--> Menghapus file/folder bernama (target) di posisi pointer sekarang
//6. tree
//--> Mencetak tree directory dari root
//7. exit
//--> Mematikan sistem

public class MekanikDirSys {
    public static void main(String[] args) {
        Scanner inputUser = new Scanner(System.in);
        MekanikDirSys directorySystem = new MekanikDirSys();
        while (inputUser.hasNextLine()) {
            String x = inputUser.nextLine();
            switch (x.split(" ")[0]) {
                case "mkdir":
                    String targetMkdir = x.substring(6);
                    directorySystem.mkdir(targetMkdir);
                    break;
                case "touch":
                    String targetTouch = x.substring(6);
                    directorySystem.touch(targetTouch);
                    break;
                case "cd":
                    String targetCd = x.substring(3);
                    directorySystem.cd(targetCd);
                    break;
                case "ls":
                    directorySystem.ls();
                    break;
                case "rm":
                    String targetRm = x.substring(3);
                    directorySystem.rm(targetRm);
                    break;
                case "tree":
                    directorySystem.tree();
                    break;
                case "exit":
                    return;
                default:
                    System.out.println("Command not recognized");
                    break;
            }
        }

    }
    Node root = new Node("root",true);
    Node pointer=root;

    MekanikDirSys() {
    }


    public void mkdir(String name) {
        //cek apakah folder sudah ada?
        if (pointer.getChild(name) != null || pointer.children.contains(name)) {
            System.out.println("folder already exist");
        } else {
            //jika belum, buat ke folder baru
            Node folderBaru = new Node(name, true);
            pointer.children.add(folderBaru);
        }
    }



    public void touch(String name) {
        //cek apakah file sudah ada?
        if (pointer.getChild(name) != null || pointer.children.contains(name)) {
            System.out.println("file already exist");
        } //jika belum, buat ke file baru
        else {
            Node fileBaru = new Node(name, false);
            pointer.children.add(fileBaru);
        }
    }

    public void cd(String name) {
        if (Objects.equals(name, "..")) {
            if (pointer != root) {
                pointer = getParent(pointer,root);
            }
        } else {
            Node currentChild = pointer.getChild(name);
            if (currentChild == null || !currentChild.cekFolder) {
                System.out.println("folder not found");
            } else {
                pointer = currentChild;
            }
        }
    }

    public void ls() {
        List<Node> sortedChildren = new ArrayList<>(pointer.children);
        sortedChildren.sort(Comparator.comparing(node -> node.name));
        StringJoiner sj = new StringJoiner(" ");
        for (Node child : sortedChildren) {
            sj.add(child.name);
        }
        System.out.println(sj.toString());
    }


    public void rm(String name) {
        boolean delete = pointer.children.removeIf(child -> child.name.equals(name));
        if (!delete) {
            System.out.println("target not found");
        }
    }


    public void treeRecursive(Node node, int depth) {
        String prefix = "  ".repeat(depth);
        if (!Objects.equals(node.name, "root")){
            System.out.println(prefix + "-- " + node.name);
        }
        node.children.forEach(child -> treeRecursive(child, depth + 1));
    }


    public void tree() {
        System.out.println("root");
        treeRecursive(root, 0);
    }

    Node getParent(Node node, Node parent) {
        Stack<Node> stack = new Stack<>();
        stack.push(parent);
        while (!stack.isEmpty()) {
            Node curr = stack.pop();
            for (Node child : curr.children) {
                if (child == node) {
                    return curr;
                } else {
                    stack.push(child);
                }
            }
        }
        return null;
    }

}

class Folder{
    String namaFolder;
    public Folder(String namaFolder){
        this.namaFolder = namaFolder;
    }
}
class FileDalamFolder extends Folder{
    public FileDalamFolder(String nama){
        super(nama);
    }
}
class Node {
    final String name;
    final boolean cekFolder;
    List<Node> children = new LinkedList<>();

    Node(String name, boolean folder) {
        this.name = name;
        this.cekFolder = folder;
    }

    Node getChild(String name) {
        if (children != null) {
            for (Node child : children) {
                if (Objects.equals(child.name, name)) {
                    return child;
                }
            }
        }
        return null;
    }
}



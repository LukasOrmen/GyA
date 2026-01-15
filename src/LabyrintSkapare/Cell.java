package LabyrintSkapare;

public class Cell {
    private int top;
    private int left;
    private int bottom;
    private int right;


    public Cell() {
        this.top = 1;
        this.left = 1;
        this.bottom = 1;
        this.right = 1;
    }

    // Get-metoder
    public int getTop() {
        return top;
    }

    public int getLeft() {
        return left;
    }

    public int getBottom() {
        return bottom;
    }

    public int getRight() {
        return right;
    }

    // Remove-metoder (lättare än att använda set-metoder i detta fall)
    public void removeTop() {
        this.top = 0;
    }

    public void removeLeft() {
        this.left = 0;
    }

    public void removeBottom() {
        this.bottom = 0;
    }

    public void removeRight() {
        this.right = 0;
    }

    // Add-metoder (tror ej detta behövs nån gång,men inte omöjligt)
    public void addTop() {
        this.top = 1;
    }

    public void addLeft() {
        this.left = 1;
    }

    public void addBottom() {
        this.bottom = 1;
    }

    public void addRight() {
        this.right = 1;
    }
}

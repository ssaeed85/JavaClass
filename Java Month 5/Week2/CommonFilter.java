
public class CommonFilter implements Filter {
    //Filters out movies common to both raters
    private Rater One;
    private Rater Two;
    public CommonFilter(Rater One, Rater Two) {
        this.One = One;
        this.Two = Two;
    }
    
    @Override
    public boolean satisfies(String id){
        if(One!=Two)
            return (One.hasRating(id) && Two.hasRating(id));
        else{
            //System.out.println("The two raters are the same");
            return true;
        }
    }
}

import java.util.List;

public class Reparto {
    private List<Actor> actor;

    @Override
    public String toString() {
        String buff = "Reparto: [";
        StringBuilder result = new StringBuilder(String.valueOf(buff));
        int size = actor.size();

        for(int i =0; i < size;i++) {
            if(i==(size-1))
                result.append("Actor: {" + actor.get(i).toString() + "}]");
            else
                result.append("Actor: {" + actor.get(i).toString() + "}, ");
        }

        return result.toString();
    }

    public List<Actor> getActor() {
        return actor;
    }
}

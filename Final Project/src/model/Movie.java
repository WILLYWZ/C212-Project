package model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static util.Flags.MOVIE_FILE;

public class Movie extends AbstractReadFile {

    private String title;
    private BigDecimal price;
    private String desc;
    // the time of movie deploy
    private String timeOfMovie;
    private String timeOfShow;
    // level
    private Rate level;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getTimeOfMovie() {
        return timeOfMovie;
    }

    public void setTimeOfMovie(String timeOfMovie) {
        this.timeOfMovie = timeOfMovie;
    }

    public String getTimeOfShow() {
        return timeOfShow;
    }

    public void setTimeOfShow(String timeOfShow) {
        this.timeOfShow = timeOfShow;
    }

    public Rate getLevel() {
        return level;
    }

    public void setLevel(Rate level) {
        this.level = level;
    }

    @Override
    public Movie readFromString(String s) {
        String[] profiles = s.split(SEPARATOR);
        Movie movie = new Movie();
        movie.setTitle(profiles[0]);
        movie.setPrice(new BigDecimal(profiles[1]));
        movie.setDesc(profiles[2]);
        movie.setTimeOfMovie(profiles[3]);
        movie.setTimeOfShow(profiles[4]);
        movie.setLevel(Rate.valueOf(profiles[5]));
        return movie;
    }

    @Override
    public String toString() {
        return title + SEPARATOR + price + SEPARATOR + desc + SEPARATOR
                + timeOfMovie + SEPARATOR + timeOfShow + SEPARATOR + level;
    }

    public static String addNew(Movie movie) {
        List<Movie> all = listAllMovie();

        List<String> names = all.stream().map(Movie::getTitle).collect(Collectors.toList());
        if (names.contains(movie.getTitle())) {
            return "movie exists!";
        }

        all.add(movie);
        try {
            saveIntoFile(MOVIE_FILE, all.stream().map(Movie::toString).collect(Collectors.toList()));
            return "done";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "some error";

    }

    public static String setOne(String name, Movie movie) {
        List<Movie> all = listAllMovie();
        System.out.println(movie.toString());

        for (int i = 0; i < all.size(); ++i) {
            if (all.get(i).getTitle().equals(name)) {
                Movie movie1 = all.get(i);
                movie1.setPrice(movie.getPrice());
                movie1.setDesc(movie.desc);
                movie1.setLevel(movie.level);
                movie1.setTimeOfMovie(movie.timeOfMovie);
                movie1.setTimeOfShow(movie.timeOfShow);

                all.set(i, movie1);
            }
        }
        try {
            saveIntoFile(MOVIE_FILE, all.stream().map(Movie::toString).collect(Collectors.toList()));
            return "done";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "some error";

    }

    public static List<Movie> listAllMovie() {
        try {

            List<String> list = readFromFile(MOVIE_FILE);
            return list.stream().map(per -> new Movie().readFromString(per)).collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }


    public static Movie getByName(String name) {
        List<Movie> all = listAllMovie();

        List<Movie> names = all.stream().filter(per -> name.equals(per.getTitle())).limit(1).collect(Collectors.toList());

        return names.size() == 0 ? null : names.get(0);
    }


    public static List<String> showtimeToList(String time) {
        List<String> resultList = new ArrayList<>();
        String[] timePerDay = time.split("], ");
        for (int i = 0; i < timePerDay.length; i++) {
            // day/month/year
            String prefix = timePerDay[i].substring(0, timePerDay[i].indexOf("["));
            // time
            String time1;
            if (i < timePerDay.length - 1) {
                time1 = timePerDay[i].substring(timePerDay[i].indexOf("[") + 1);
            } else {
                time1 = timePerDay[i].substring(timePerDay[i].indexOf("[") + 1, timePerDay[i].length() - 1);
            }

            String[] timeArr = time1.split(", ");
            for (int j = 0; j < timeArr.length; j++) {
                resultList.add(prefix + timeArr[j]);

            }
        }
        return resultList;
    }

    public enum Rate {
        G(0), PG(1), PG_13(2), R(3), NC_17(4);


        private int value;

        Rate(int i) {
            value = i;
        }
    }

}
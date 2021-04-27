package com.movie_aggregator.entity;

import com.jayway.jsonpath.*;
import com.movie_aggregator.utils.PropertiesLoader;

import javax.persistence.*;
import java.util.Properties;

/**
 * @author mturchanov
 */

@Table(name = "review_sources")
@Entity(name = "ReviewSource")
public class ReviewSource implements PropertiesLoader {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Transient
    private Properties properties;

    @Column
    private String film;
    @Column
    private String film_web_id_pl;
    @Column
    private String film_web_name_pl;
    @Column
    private String film_web_pl;
    @Column
    private String all_cinema_jp;
    @Column
    private String allocine_fr;
    @Column
    private String cine_gr;
    @Column
    private String cinema_de;
    @Column
    private String common_sense;
    @Column
    private String eiga_jp;
    @Column
    private String film_affinity;
    @Column
    private String filmfront_no;
    @Column
    private String film_tv_it;
    @Column
    private String google_play_tv;
    @Column
    private String kinenote_jp;
    @Column
    private String kvikmyndir_is;
    @Column
    private String ldif_de;
    @Column
    private String letterbox;
    @Column
    private String metacritic;
    @Column
    private String mrqe;
    @Column
    private String movie_walker_jp;
    @Column
    private String moviemeter_nl;
    @Column
    private String movies_anywhere;
    @Column
    private String mubi;
    @Column
    private String mymovies_it;
    @Column
    private String netflix;
    @Column
    private String port_hu;
    @Column
    private String quora_topic;
    @Column
    private String rotten_tomatoes;
    @Column
    private String scope_dk;
    @Column
    private String sratim_il;
    @Column
    private String tmdb;
    @Column
    private String tv_com;
    @Column
    private String anidb;
    @Column
    private String anime_news_newtwork;
    @Column
    private String anime_click;
    @Column
    private String imfdb;
    @Column
    private String mal;
    @Column
    private String trakt_tv;
    @Column
    private String anilist;


    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "movie_id", referencedColumnName = "id")
    private Movie movie;

    public ReviewSource(String sparql) {
        properties = loadProperties("/reviewSources.properties");

        film = JsonPath.read(sparql, "$.film.value");

        if (sparql.contains("film_web_id_pl")) {
        }
        if (sparql.contains("film_web_name_pl") && sparql.contains("film_web_id_pl")) {
            film_web_name_pl = JsonPath.read(sparql, "$.film_web_name_pl.value");
            film_web_id_pl =  JsonPath.read(sparql, "$.film_web_id_pl.value");
            film_web_pl = String.format(properties.getProperty("film_web_pl"),
                    film_web_name_pl, "1994", film_web_id_pl);
        }

        if (sparql.contains("all_cinema_jp")) {
            String all_cinema_jpIdentifier = JsonPath.read(sparql, "$.all_cinema_jp.value");
            all_cinema_jp = String.format(properties.getProperty("all_cinema_jp"), all_cinema_jpIdentifier);
        }
        if (sparql.contains("allcine_fr")) {
            String allcine_frIdentifier = JsonPath.read(sparql, "$.allcine_fr.value");
            allocine_fr = String.format(properties.getProperty("allcine_fr"), allcine_frIdentifier);
        }
        if (sparql.contains("cine_gr")) {
            String cine_grIdentifier = JsonPath.read(sparql, "$.cine_gr.value");
            cine_gr = String.format(properties.getProperty("cine_gr"), cine_grIdentifier);
        }
        if (sparql.contains("cinema_de")) {
            String cinema_deIdentifier = JsonPath.read(sparql, "$.cinema_de.value");
            cinema_de = String.format(properties.getProperty("cinema_de"), cinema_deIdentifier);
        }
        if (sparql.contains("common_sense")) {
            String common_senseIdentifier = JsonPath.read(sparql, "$.common_sense.value");
            common_sense = String.format(properties.getProperty("common_sense"), common_senseIdentifier);

        }
        if (sparql.contains("eiga_jp")) {
            String eiga_jpIdentifier = JsonPath.read(sparql, "$.eiga_jp.value");
            eiga_jp = String.format(properties.getProperty("eiga_jp"), eiga_jpIdentifier);
        }
        if (sparql.contains("film_affinity")) {
            String film_affinityIdentifier = JsonPath.read(sparql, "$.film_affinity.value");
            film_affinity = String.format(properties.getProperty("film_affinity"), film_affinityIdentifier);
        }
        if (sparql.contains("filmfront_no")) {
            String filmfront_noIdentifier = JsonPath.read(sparql, "$.filmfront_no.value");
            filmfront_no = String.format(properties.getProperty("filmfront_no"), filmfront_noIdentifier);
        }
        if (sparql.contains("film_tv_it")) {
            String film_tv_itIdentifier = JsonPath.read(sparql, "$.film_tv_it.value");
            film_tv_it = String.format(properties.getProperty("film_tv_it"), film_tv_itIdentifier);
        }
        if (sparql.contains("google_play_tv")) {
            String google_play_tvIdentifier = JsonPath.read(sparql, "$.google_play_tv.value");
            google_play_tv = String.format(properties.getProperty("google_play_tv"), google_play_tvIdentifier);
        }
        if (sparql.contains("kinenote_jp")) {
            String kinenote_jpIdentifier = JsonPath.read(sparql, "$.kinenote_jp.value");
            kinenote_jp = String.format(properties.getProperty("kinenote_jp"), kinenote_jpIdentifier);
        }
        if (sparql.contains("kvikmyndir_is")) {
            String kvikmyndir_isIdentifier = JsonPath.read(sparql, "$.kvikmyndir_is.value");
            kvikmyndir_is = String.format(properties.getProperty("kvikmyndir_is"), kvikmyndir_isIdentifier);
        }
        if (sparql.contains("ldif_de")) {
            String ldif_deIdentifier = JsonPath.read(sparql, "$.ldif_de.value");
            ldif_de = String.format(properties.getProperty("ldif_de"), ldif_deIdentifier);
        }
        if (sparql.contains("letterbox")) {
            String letterboxIdentifier = JsonPath.read(sparql, "$.letterbox.value");
            letterbox = String.format(properties.getProperty("letterbox"), letterboxIdentifier);
        }
        if (sparql.contains("metacritic")) {
            String metacriticIdentifier = JsonPath.read(sparql, "$.metacritic.value");
            metacritic = String.format(properties.getProperty("metacritic"), metacriticIdentifier);
        }
        if (sparql.contains("mrqe")) {
            String mrqeIdentifier = JsonPath.read(sparql, "$.mrqe.value");
            mrqe = String.format(properties.getProperty("mrqe"), mrqeIdentifier);
        }
        if (sparql.contains("movie_walker_jp")) {
            String movie_walker_jpIdentifier = JsonPath.read(sparql, "$.movie_walker_jp.value");
            movie_walker_jp = String.format(properties.getProperty("movie_walker_jp"), movie_walker_jpIdentifier);
        }
        if (sparql.contains("moviemeter_nl")) {
            String moviemeter_nlIdentifier = JsonPath.read(sparql, "$.moviemeter_nl.value");
            moviemeter_nl = String.format(properties.getProperty("moviemeter_nl"), moviemeter_nlIdentifier);
        }
        if (sparql.contains("movies_anywhere")) {
            String movies_anywhereIdentifier = JsonPath.read(sparql, "$.movies_anywhere.value");
            movies_anywhere = String.format(properties.getProperty("movies_anywhere"), movies_anywhereIdentifier);
        }
        if (sparql.contains("mubi")) {
            String mubiIdentifier = JsonPath.read(sparql, "$.mubi.value");
            mubi = String.format(properties.getProperty("mubi"), mubiIdentifier);
        }
        if (sparql.contains("mymovies_it")) {
            String mymovies_itIdentifier = JsonPath.read(sparql, "$.mymovies_it.value");
            mymovies_it = String.format(properties.getProperty("mymovies_it"), mymovies_itIdentifier);
        }
        if (sparql.contains("netflix")) {
            String netflixIdentifier = JsonPath.read(sparql, "$.netflix.value");
            netflix = String.format(properties.getProperty("netflix"), netflixIdentifier);
        }
        if (sparql.contains("port_hu")) {
            String port_huIdentifier = JsonPath.read(sparql, "$.port_hu.value");
            port_hu = String.format(properties.getProperty("port_hu"), port_huIdentifier);
        }
        if (sparql.contains("quora_topic")) {
            String quora_topicIdentifier = JsonPath.read(sparql, "$.quora_topic.value");
            quora_topic = String.format(properties.getProperty("quora_topic"), quora_topicIdentifier);
        }
        if (sparql.contains("rotten_tomatoes")) {
            String rotten_tomatoesIdentifier = JsonPath.read(sparql, "$.rotten_tomatoes.value");
            rotten_tomatoes = String.format(properties.getProperty("rotten_tomatoes"), rotten_tomatoesIdentifier);
        }
        if (sparql.contains("scope_dk")) {
            String scope_dkIdentifier = JsonPath.read(sparql, "$.scope_dk.value");
            scope_dk = String.format(properties.getProperty("scope_dk"), scope_dkIdentifier);
        }
        if (sparql.contains("sratim_il")) {
            String sratim_ilIdentifier = JsonPath.read(sparql, "$.sratim_il.value");
            sratim_il = String.format(properties.getProperty("sratim_il"), sratim_ilIdentifier);
        }
        if (sparql.contains("tmdb")) {
            String tmdbIdentifier = JsonPath.read(sparql, "$.tmdb.value");
            tmdb = String.format(properties.getProperty("tmdb"), tmdbIdentifier);
        }
        if (sparql.contains("tv_com")) {
            String tv_comIdentifier = JsonPath.read(sparql, "$.tv_com.value");
            tv_com = String.format(properties.getProperty("tv_com"), tv_comIdentifier);
        }
        if (sparql.contains("anidb")) {
            String anidbIdentifier = JsonPath.read(sparql, "$.anidb.value");
            anidb = String.format(properties.getProperty("anidb"), anidbIdentifier);
        }
        if (sparql.contains("anime_news_newtwork")) {
            String anime_news_newtworkIdentifier = JsonPath.read(sparql, "$.anime_news_newtwork.value");
            anime_news_newtwork = String.format(properties.getProperty("anime_news_newtwork"), anime_news_newtworkIdentifier);
        }
        if (sparql.contains("anime_click")) {
            String anime_clickIdentifier = JsonPath.read(sparql, "$.anime_click.value");
            anime_click = String.format(properties.getProperty("anime_click"), anime_clickIdentifier);
        }
        if (sparql.contains("imfdb")) {
            String imfdbIdentifier = JsonPath.read(sparql, "$.imfdb.value");
            imfdb = String.format(properties.getProperty("imfdb"), imfdbIdentifier);
        }
        if (sparql.contains("mal")) {
            String malIdentifier = JsonPath.read(sparql, "$.mal.value");
            mal = String.format(properties.getProperty("mal"), malIdentifier);
        }
        if (sparql.contains("trakt_tv")) {
            String trakt_tvIdentifier = JsonPath.read(sparql, "$.trakt_tv.value");
            trakt_tv = String.format(properties.getProperty("trakt_tv"), trakt_tvIdentifier);
        }
        if (sparql.contains("anilist")) {
            String anilistIdentifier = JsonPath.read(sparql, "$.anilist.value");
            anilist = String.format(properties.getProperty("anilist"), anilistIdentifier);
        }

    }

    public ReviewSource() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFilm() {
        return film;
    }

    public void setFilm(String film) {
        this.film = film;
    }

    public String getFilm_web_id_pl() {
        return film_web_id_pl;
    }

    public void setFilm_web_id_pl(String film_web_id_pl) {
        this.film_web_id_pl = film_web_id_pl;
    }

    public String getFilm_web_name_pl() {
        return film_web_name_pl;
    }

    public void setFilm_web_name_pl(String film_web_name_pl) {
        this.film_web_name_pl = film_web_name_pl;
    }

    public String getAll_cinema_jp() {
        return all_cinema_jp;
    }

    public void setAll_cinema_jp(String all_cinema_jp) {
        this.all_cinema_jp = all_cinema_jp;
    }

    public String getAllocine_fr() {
        return allocine_fr;
    }

    public void setAllocine_fr(String allcine_fr) {
        this.allocine_fr = allcine_fr;
    }

    public String getCine_gr() {
        return cine_gr;
    }

    public void setCine_gr(String cine_gr) {
        this.cine_gr = cine_gr;
    }

    public String getCinema_de() {
        return cinema_de;
    }

    public void setCinema_de(String cinema_de) {
        this.cinema_de = cinema_de;
    }

    public String getCommon_sense() {
        return common_sense;
    }

    public void setCommon_sense(String common_sense) {
        this.common_sense = common_sense;
    }

    public String getEiga_jp() {
        return eiga_jp;
    }

    public void setEiga_jp(String eiga_jp) {
        this.eiga_jp = eiga_jp;
    }

    public String getFilm_affinity() {
        return film_affinity;
    }

    public void setFilm_affinity(String film_affinity) {
        this.film_affinity = film_affinity;
    }

    public String getFilmfront_no() {
        return filmfront_no;
    }

    public void setFilmfront_no(String filmfront_no) {
        this.filmfront_no = filmfront_no;
    }

    public String getFilm_tv_it() {
        return film_tv_it;
    }

    public void setFilm_tv_it(String film_tv_it) {
        this.film_tv_it = film_tv_it;
    }

    public String getGoogle_play_tv() {
        return google_play_tv;
    }

    public void setGoogle_play_tv(String google_play_tv) {
        this.google_play_tv = google_play_tv;
    }

    public String getKinenote_jp() {
        return kinenote_jp;
    }

    public void setKinenote_jp(String kinenote_jp) {
        this.kinenote_jp = kinenote_jp;
    }

    public String getKvikmyndir_is() {
        return kvikmyndir_is;
    }

    public void setKvikmyndir_is(String kvikmyndir_is) {
        this.kvikmyndir_is = kvikmyndir_is;
    }

    public String getLdif_de() {
        return ldif_de;
    }

    public void setLdif_de(String ldif_de) {
        this.ldif_de = ldif_de;
    }

    public String getLetterbox() {
        return letterbox;
    }

    public void setLetterbox(String letterbox) {
        this.letterbox = letterbox;
    }

    public String getMetacritic() {
        return metacritic;
    }

    public void setMetacritic(String metacritic) {
        this.metacritic = metacritic;
    }

    public String getMrqe() {
        return mrqe;
    }

    public void setMrqe(String mrqe) {
        this.mrqe = mrqe;
    }

    public String getMovie_walker_jp() {
        return movie_walker_jp;
    }

    public void setMovie_walker_jp(String movie_walker_jp) {
        this.movie_walker_jp = movie_walker_jp;
    }

    public String getMoviemeter_nl() {
        return moviemeter_nl;
    }

    public void setMoviemeter_nl(String moviemeter_nl) {
        this.moviemeter_nl = moviemeter_nl;
    }

    public String getMovies_anywhere() {
        return movies_anywhere;
    }

    public void setMovies_anywhere(String movies_anywhere) {
        this.movies_anywhere = movies_anywhere;
    }

    public String getMubi() {
        return mubi;
    }

    public void setMubi(String mubi) {
        this.mubi = mubi;
    }

    public String getMymovies_it() {
        return mymovies_it;
    }

    public void setMymovies_it(String mymovies_it) {
        this.mymovies_it = mymovies_it;
    }

    public String getNetflix() {
        return netflix;
    }

    public void setNetflix(String netflix) {
        this.netflix = netflix;
    }

    public String getPort_hu() {
        return port_hu;
    }

    public void setPort_hu(String port_hu) {
        this.port_hu = port_hu;
    }

    public String getQuora_topic() {
        return quora_topic;
    }

    public void setQuora_topic(String quora_topic) {
        this.quora_topic = quora_topic;
    }

    public String getRotten_tomatoes() {
        return rotten_tomatoes;
    }

    public void setRotten_tomatoes(String rotten_tomatoes) {
        this.rotten_tomatoes = rotten_tomatoes;
    }

    public String getScope_dk() {
        return scope_dk;
    }

    public void setScope_dk(String skope_dk) {
        this.scope_dk = skope_dk;
    }

    public String getSratim_il() {
        return sratim_il;
    }

    public void setSratim_il(String sratim_il) {
        this.sratim_il = sratim_il;
    }

    public String getTmdb() {
        return tmdb;
    }

    public void setTmdb(String tmdb) {
        this.tmdb = tmdb;
    }

    public String getTv_com() {
        return tv_com;
    }

    public void setTv_com(String tv_com) {
        this.tv_com = tv_com;
    }

    public String getAnidb() {
        return anidb;
    }

    public void setAnidb(String anidb) {
        this.anidb = anidb;
    }

    public String getAnime_news_newtwork() {
        return anime_news_newtwork;
    }

    public void setAnime_news_newtwork(String anime_news_newtwork) {
        this.anime_news_newtwork = anime_news_newtwork;
    }

    public String getAnime_click() {
        return anime_click;
    }

    public void setAnime_click(String anime_click) {
        this.anime_click = anime_click;
    }

    public String getImfdb() {
        return imfdb;
    }

    public void setImfdb(String imfdb) {
        this.imfdb = imfdb;
    }

    public String getMal() {
        return mal;
    }

    public void setMal(String mal) {
        this.mal = mal;
    }

    public String getTrakt_tv() {
        return trakt_tv;
    }

    public void setTrakt_tv(String trakt_tv) {
        this.trakt_tv = trakt_tv;
    }

    public String getAnilist() {
        return anilist;
    }

    public void setAnilist(String anilist) {
        this.anilist = anilist;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    @Override
    public String toString() {
        return "ReviewSource{" +
                "id=" + id +
                ", properties=" + properties +
                ", film='" + film + '\'' +
                ", film_web_id_pl='" + film_web_id_pl + '\'' +
                ", film_web_name_pl='" + film_web_name_pl + '\'' +
                ", film_web_pl='" + film_web_pl + '\'' +
                ", all_cinema_jp='" + all_cinema_jp + '\'' +
                ", allocine_fr='" + allocine_fr + '\'' +
                ", cine_gr='" + cine_gr + '\'' +
                ", cinema_de='" + cinema_de + '\'' +
                ", common_sense='" + common_sense + '\'' +
                ", eiga_jp='" + eiga_jp + '\'' +
                ", film_affinity='" + film_affinity + '\'' +
                ", filmfront_no='" + filmfront_no + '\'' +
                ", film_tv_it='" + film_tv_it + '\'' +
                ", google_play_tv='" + google_play_tv + '\'' +
                ", kinenote_jp='" + kinenote_jp + '\'' +
                ", kvikmyndir_is='" + kvikmyndir_is + '\'' +
                ", ldif_de='" + ldif_de + '\'' +
                ", letterbox='" + letterbox + '\'' +
                ", metacritic='" + metacritic + '\'' +
                ", mrqe='" + mrqe + '\'' +
                ", movie_walker_jp='" + movie_walker_jp + '\'' +
                ", moviemeter_nl='" + moviemeter_nl + '\'' +
                ", movies_anywhere='" + movies_anywhere + '\'' +
                ", mubi='" + mubi + '\'' +
                ", mymovies_it='" + mymovies_it + '\'' +
                ", netflix='" + netflix + '\'' +
                ", port_hu='" + port_hu + '\'' +
                ", quora_topic='" + quora_topic + '\'' +
                ", rotten_tomatoes='" + rotten_tomatoes + '\'' +
                ", scope_dk='" + scope_dk + '\'' +
                ", sratim_il='" + sratim_il + '\'' +
                ", tmdb='" + tmdb + '\'' +
                ", tv_com='" + tv_com + '\'' +
                ", anidb='" + anidb + '\'' +
                ", anime_news_newtwork='" + anime_news_newtwork + '\'' +
                ", anime_click='" + anime_click + '\'' +
                ", imfdb='" + imfdb + '\'' +
                ", mal='" + mal + '\'' +
                ", trakt_tv='" + trakt_tv + '\'' +
                ", anilist='" + anilist + '\'' +
                ", movie=" + movie +
                '}';
    }
}
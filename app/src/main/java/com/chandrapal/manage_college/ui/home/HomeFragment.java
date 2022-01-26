package com.chandrapal.manage_college.ui.home;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.chandrapal.manage_college.FullNotesActivity;
import com.chandrapal.manage_college.GpsTracker;
import com.chandrapal.manage_college.MainActivity;
import com.chandrapal.manage_college.Model;
import com.chandrapal.manage_college.R;
import com.chandrapal.manage_college.SettingsActivity;
import com.chandrapal.manage_college.UploadNotesActivity;
import com.chandrapal.manage_college.UserProfileActivity;
import com.chandrapal.manage_college.WebViewActivity;
import com.chandrapal.manage_college.databinding.FragmentHomeBinding;
import com.chandrapal.manage_college.ui.dashboard.AdapterStudentsDashboardFragment;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static android.content.ContentValues.TAG;

public class HomeFragment extends Fragment implements AdapterHome.OnShowMoreListener, AdapterHome.OnProfileClickListener {

    private HomeViewModel homeViewModel;
    private String message, message2;

    boolean action_listview_home_menu_state = false;
    boolean action_small_cardview_home_menu_state = false;
    boolean action_cardview_home_menu_state = false;
    boolean action_search_home_menu_state = false;

    Menu menuGlobal;
    MenuItem action_small_cardview_home_menu, action_listview_home_menu, action_cardview_home_menu, action_search_home_menu, action_settings_home_menu, action_faq_home_menu, action_about_us_home_menu, action_visit_website_home_menu ;
    Toolbar toolbarHome;
    AdapterHome adapterHome;
    private FragmentHomeBinding binding;
    private List<Model> notesHolder;
    private List<Model> usersHolder;

    private String usersResponse = "";
    private final String WEB_URL_GET_USERS = "https://manage-college.000webhostapp.com/get_users.php";
    private final String WEB_URL_GET_NOTES = "https://manage-college.000webhostapp.com/get_notes.php";
    private final String DIR = "https://manage-college.000webhostapp.com/upload_image/images/";
    private final String DEFAULT_USER_RESPONSE = "[{\"message\":\"positive\",\"id\":\"151\",\"fullname\":\"demo33333\",\"reg_num\":\"397265\",\"username\":\"demo33333\",\"password\":\"$2y$10$HF12OcDrPcRvv6\\/csUCfvOuSUrTkXihOCnDPTVNeQlzu391zU6TcO\",\"phone\":\"\",\"email\":\"\",\"bio\":\"\",\"u_type\":\"student\",\"profile_image\":\"1648251706.png\",\"status\":\"disable\"},{\"message\":\"positive\",\"id\":\"148\",\"fullname\":\"demo3333\",\"reg_num\":\"711885\",\"username\":\"demo3333\",\"password\":\"$2y$10$vCExEt94zGRJYPSQy6uFVe6HdaJs6r1epzsSy6Z7BkguMyR5pHRIS\",\"phone\":\"\",\"email\":\"\",\"bio\":\"\",\"u_type\":\"student\",\"profile_image\":\"1406992643.jpg\",\"status\":\"disable\"},{\"message\":\"positive\",\"id\":\"147\",\"fullname\":\"demo\",\"reg_num\":\"520842\",\"username\":\"demo\",\"password\":\"$2y$10$4iqjSIFPJtKgP7YSQAKYE.TbP72cvvEmSt\\/Fwi2LuFEwOpUhikVV2\",\"phone\":\"\",\"email\":\"\",\"bio\":\"\",\"u_type\":\"student\",\"profile_image\":\"189194924.png\",\"status\":\"disable\"},{\"message\":\"positive\",\"id\":\"145\",\"fullname\":\"chandrapal300000\",\"reg_num\":\"505900\",\"username\":\"chandrapal300000\",\"password\":\"$2y$10$m9Cuim\\/objiOejXp5HTGKegIEt6lORNZPGSVHX6gBNPjEpvt1k5R6\",\"phone\":\"\",\"email\":\"\",\"bio\":\"\",\"u_type\":\"student\",\"profile_image\":\"1353210925.jpg\",\"status\":\"disable\"},{\"message\":\"positive\",\"id\":\"137\",\"fullname\":\"chandrapal3\",\"reg_num\":\"429553\",\"username\":\"chandrapal3\",\"password\":\"$2y$10$qm99oSx5r.iMSt1x1JA38uq6pjj5um\\/EW\\/2gCQTeSM5KDJTDKWC1O\",\"phone\":\"\",\"email\":\"\",\"bio\":\"\",\"u_type\":\"student\",\"profile_image\":\"1336912770.png\",\"status\":\"disable\"},{\"message\":\"positive\",\"id\":\"135\",\"fullname\":\"chandrapal300\",\"reg_num\":\"865908\",\"username\":\"chandrapal300\",\"password\":\"$2y$10$oUxdvsSEzf.8\\/fZIyFGIWe3tBn3OxVXJN18Zq9cBEtu5bcElD8xAi\",\"phone\":\"\",\"email\":\"\",\"bio\":\"\",\"u_type\":\"student\",\"profile_image\":\"1712724708.png\",\"status\":\"disable\"},{\"message\":\"positive\",\"id\":\"128\",\"fullname\":\"chandrapal30000\",\"reg_num\":\"116520\",\"username\":\"chandrapal30000\",\"password\":\"$2y$10$uhHTyjuWGdXWJuxWadHgc.7smPatP1YVESEzc4g.\\/\\/qlC\\/E52XJJm\",\"phone\":\"\",\"email\":\"\",\"bio\":\"\",\"u_type\":\"student\",\"profile_image\":\"189194924.png\",\"status\":\"disable\"},{\"message\":\"positive\",\"id\":\"126\",\"fullname\":\"demo0001\",\"reg_num\":\"767428\",\"username\":\"demo0001\",\"password\":\"$2y$10$lnOefU.TXnOiUiX.WNl0Feu6icZ9xv\\/CaS\\/wJuAq5aOzPL2DHwIwa\",\"phone\":\"\",\"email\":\"\",\"bio\":\"\",\"u_type\":\"student\",\"profile_image\":\"1566859113.png\",\"status\":\"disable\"},{\"message\":\"positive\",\"id\":\"124\",\"fullname\":\"chandrapal07\",\"reg_num\":\"550451\",\"username\":\"chandrapal07\",\"password\":\"$2y$10$0dqQiXnD7vyMOC25uNo0k.bxKaSF.LkpeRGZYfN02TcmC.hH8Jywa\",\"phone\":\"\",\"email\":\"\",\"bio\":\"\",\"u_type\":\"student\",\"profile_image\":\"1748189249.jpg\",\"status\":\"disable\"},{\"message\":\"positive\",\"id\":\"123\",\"fullname\":\"chandrapal06\",\"reg_num\":\"182020\",\"username\":\"chandrapal06\",\"password\":\"$2y$10$al265JaxM9gmec7U4Ii7aOlTgQCFnlR5RJkFCn5gOOKqpGBd9YFn6\",\"phone\":\"\",\"email\":\"\",\"bio\":\"\",\"u_type\":\"student\",\"profile_image\":\"1712724708.png\",\"status\":\"disable\"},{\"message\":\"positive\",\"id\":\"121\",\"fullname\":\"chandrapal05\",\"reg_num\":\"517045\",\"username\":\"chandrapal05\",\"password\":\"$2y$10$XImmO6\\/2XrVqtqV2585sjuSjy0fID7iVb8OsAE5juRzi0XAetwfAi\",\"phone\":\"\",\"email\":\"\",\"bio\":\"\",\"u_type\":\"student\",\"profile_image\":\"1926159400.png\",\"status\":\"disable\"},{\"message\":\"positive\",\"id\":\"119\",\"fullname\":\"chandrapal04\",\"reg_num\":\"937890\",\"username\":\"chandrapal04\",\"password\":\"$2y$10$N39jFGsUqjv5nphdFZRl5.T9d.HepqQ8vIWAJD7X\\/kkIu.Wfz1dma\",\"phone\":\"\",\"email\":\"\",\"bio\":\"\",\"u_type\":\"student\",\"profile_image\":\"189194924.png\",\"status\":\"disable\"},{\"message\":\"positive\",\"id\":\"118\",\"fullname\":\"chandrapal03\",\"reg_num\":\"771870\",\"username\":\"chandrapal03\",\"password\":\"$2y$10$i2d8mnvXUUDrtyvg5mMk1uAGWj4smFPHFA4qEmxknIbuixBbGDbIu\",\"phone\":\"\",\"email\":\"\",\"bio\":\"\",\"u_type\":\"student\",\"profile_image\":\"955813546.jpg\",\"status\":\"disable\"},{\"message\":\"positive\",\"id\":\"117\",\"fullname\":\"chandrapal02\",\"reg_num\":\"802931\",\"username\":\"chandrapal02\",\"password\":\"$2y$10$nIBmGQZf1MLeVxWPXT2CdOEU.YjqbD3G8wGhvy4qO3ukgDC51xN9i\",\"phone\":\"\",\"email\":\"\",\"bio\":\"\",\"u_type\":\"student\",\"profile_image\":\"1783394362.png\",\"status\":\"disable\"},{\"message\":\"positive\",\"id\":\"115\",\"fullname\":\"chandrapal01\",\"reg_num\":\"204478\",\"username\":\"chandrapal01\",\"password\":\"$2y$10$LGmCEs6O6dtFxIXm0lMd1erIhAPNGaaOP2awJ94Xq1MHT235pF\\/WW\",\"phone\":\"\",\"email\":\"\",\"bio\":\"\",\"u_type\":\"student\",\"profile_image\":\"1783394362.png\",\"status\":\"disable\"},{\"message\":\"positive\",\"id\":\"114\",\"fullname\":\"chandrapal9000\",\"reg_num\":\"360904\",\"username\":\"chandrapal9000\",\"password\":\"$2y$10$48r4MwFmJD\\/wmwLc3igf8ufQWjQv.2EujcjvNoltwKeZUEOwn6g9m\",\"phone\":\"\",\"email\":\"\",\"bio\":\"\",\"u_type\":\"student\",\"profile_image\":\"928998975.png\",\"status\":\"disable\"},{\"message\":\"positive\",\"id\":\"113\",\"fullname\":\"chandrapal8000\",\"reg_num\":\"242513\",\"username\":\"chandrapal8000\",\"password\":\"$2y$10$SS1f2oDFBaAPKq069GWH1eu6bZTj55rMyyj.uzeVR.tdPYOall3Dq\",\"phone\":\"\",\"email\":\"\",\"bio\":\"\",\"u_type\":\"student\",\"profile_image\":\"1926159400.png\",\"status\":\"disable\"},{\"message\":\"positive\",\"id\":\"111\",\"fullname\":\"chandrapal7000\",\"reg_num\":\"765216\",\"username\":\"chandrapal7000\",\"password\":\"$2y$10$\\/DSYqVVMLwBOjOZJvWIEqup7XgSPayQre41\\/uNj\\/7zJB\\/O4ZelRIK\",\"phone\":\"\",\"email\":\"\",\"bio\":\"\",\"u_type\":\"student\",\"profile_image\":\"1748189249.jpg\",\"status\":\"disable\"},{\"message\":\"positive\",\"id\":\"110\",\"fullname\":\"chandrapal\",\"reg_num\":\"346740\",\"username\":\"chandrapal6000\",\"password\":\"$2y$10$TGSAJtceeIHuLWpRRUa1QOGByCZ8c390SCuhL1uOV0Fa4A5TRrfMy\",\"phone\":\"\",\"email\":\"chandrapal@gmail\",\"bio\":\"\",\"u_type\":\"student\",\"profile_image\":\"189194924.png\",\"status\":\"disable\"},{\"message\":\"positive\",\"id\":\"109\",\"fullname\":\"chandrapal\",\"reg_num\":\"201245\",\"username\":\"chandrapal5000\",\"password\":\"$2y$10$v8Q7fro0raB33x7q27HVr.ArW3aW6kUQ\\/omjq\\/ijgHnzLlCyRPv4C\",\"phone\":\"\",\"email\":\"chandrapal@gmail\",\"bio\":\"\",\"u_type\":\"student\",\"profile_image\":\"1648251706.png\",\"status\":\"disable\"},{\"message\":\"positive\",\"id\":\"108\",\"fullname\":\"chandrapal\",\"reg_num\":\"585850\",\"username\":\"chandrapal4000\",\"password\":\"$2y$10$aPXF05vGI51gxN1ZCB\\/oRu7aXJ\\/TBdzOa6vVEcryF3pDAwbGTlMea\",\"phone\":\"\",\"email\":\"chandrapal@gmail\",\"bio\":\"\",\"u_type\":\"student\",\"profile_image\":\"1353210925.jpg\",\"status\":\"disable\"},{\"message\":\"positive\",\"id\":\"102\",\"fullname\":\"demo3000\",\"reg_num\":\"484137\",\"username\":\"demo3000\",\"password\":\"$2y$10$OAmRvCAWqbdPxLLfBN2QJOBemzP5t6XbQs0A3EP8ASmHBEPGusgc.\",\"phone\":\"\",\"email\":\"demo3000@gmail\",\"bio\":\"\",\"u_type\":\"student\",\"profile_image\":\"1836400362.jpg\",\"status\":\"disable\"},{\"message\":\"positive\",\"id\":\"97\",\"fullname\":\"chandrapalsingh3000\",\"reg_num\":\"744173\",\"username\":\"chandrapalsingh3000\",\"password\":\"$2y$10$R\\/TU4agxncrvnj2AkR0RoOxCaZ3\\/0Ri7uYROHFHKgpezsr5eti2pS\",\"phone\":\"\",\"email\":\"\",\"bio\":\"Hey, I am chandrapal, This is my second account\",\"u_type\":\"student\",\"profile_image\":\"1151279121.jpg\",\"status\":\"enable\"},{\"message\":\"positive\",\"id\":\"96\",\"fullname\":\"demotoday\",\"reg_num\":\"328208\",\"username\":\"demotoday\",\"password\":\"$2y$10$JkKS3izifWRZQWpTx\\/KoeuoGlXIuJOy91idNFymbqr2iV7Dy\\/QSlO\",\"phone\":\"\",\"email\":\"demo@gmail\",\"bio\":\"\",\"u_type\":\"student\",\"profile_image\":\"928998975.png\",\"status\":\"disable\"},{\"message\":\"positive\",\"id\":\"95\",\"fullname\":\"demo300\",\"reg_num\":\"778603\",\"username\":\"demo300\",\"password\":\"$2y$10$qd0jU4h.V.uwSdMa6vIbweaFl5yS4N.RyE\\/F\\/2pV5.w3BRDasgjOu\",\"phone\":\"\",\"email\":\"\",\"bio\":\"\",\"u_type\":\"student\",\"profile_image\":\"1836400362.jpg\",\"status\":\"disable\"},{\"message\":\"positive\",\"id\":\"94\",\"fullname\":\"principal\",\"reg_num\":\"793611\",\"username\":\"principal\",\"password\":\"$2y$10$0nqm2zNkfdJJbfD22r1Z5O4bwlHYWHEMV7bejLZwXPNYTuJUMFjvq\",\"phone\":\"2384343948\",\"email\":\"principal@gmail.com\",\"bio\":\"Hey its me admin, Contact me to activate your account\",\"u_type\":\"admin\",\"profile_image\":\"17974426.png\",\"status\":\"disable\"},{\"message\":\"positive\",\"id\":\"93\",\"fullname\":\"demo200\",\"reg_num\":\"925956\",\"username\":\"demo200\",\"password\":\"$2y$10$mCBX2xl2HvwuE4Jr\\/NVz9.UoUiLm9Wvyp.Y6t0vD29j8fGngpoQoW\",\"phone\":\"\",\"email\":\"\",\"bio\":\"\",\"u_type\":\"student\",\"profile_image\":\"1826759578.png\",\"status\":\"disable\"},{\"message\":\"positive\",\"id\":\"92\",\"fullname\":\"demo sharma\",\"reg_num\":\"953283\",\"username\":\"demo100\",\"password\":\"$2y$10$8iu8VM\\/acAGQ8K2LATCQLeGQKsj.pvD6rHB\\/DDkHWSZ0OccE8GR3y\",\"phone\":\"828326723\",\"email\":\"demo100@gmail.com\",\"bio\":\"this is a demo account\",\"u_type\":\"student\",\"profile_image\":\"1336912770.png\",\"status\":\"disable\"},{\"message\":\"positive\",\"id\":\"91\",\"fullname\":\"ankush_sir\",\"reg_num\":\"607979\",\"username\":\"ankush_sir\",\"password\":\"$2y$10$6tg3eOE9Ls7861xvMJR.9e5lJFmtmW60dIR8ke6wHw2fKuM2wLKtq\",\"phone\":\"\",\"email\":\"\",\"bio\":\"\",\"u_type\":\"teacher\",\"profile_image\":\"1648251706.png\",\"status\":\"disable\"},{\"message\":\"positive\",\"id\":\"89\",\"fullname\":\"Ankit Bhadu\",\"reg_num\":\"236155\",\"username\":\"Ankit_Bhadu\",\"password\":\"$2y$10$0WLNXII2Me0rWkhdZMLSh.1U17XAXJ1okAeQF8G6ZIFqjOrOQmprm\",\"phone\":\"\",\"email\":\"anyone@gmail.com\",\"bio\":\"\",\"u_type\":\"student\",\"profile_image\":\"928998975.png\",\"status\":\"enable\"},{\"message\":\"positive\",\"id\":\"86\",\"fullname\":\"Ankit Bhadu\",\"reg_num\":\"648289\",\"username\":\"Ankit Bhadu\",\"password\":\"$2y$10$eOLz.KS2LUQ.KswJgDcA4O0craQYA7A6LrlnC8vvsZ0CIV5AUwQia\",\"phone\":\"1234567890\",\"email\":\"ankit@gmail.com\",\"bio\":\"Hi\",\"u_type\":\"student\",\"profile_image\":\"1336912770.png\",\"status\":\"enable\"},{\"message\":\"positive\",\"id\":\"84\",\"fullname\":\"Ankit\",\"reg_num\":\"770121\",\"username\":\"Ankit\",\"password\":\"$2y$10$ZcAF8vIYbdnWi5zkHWjgMOgPPDJ9tos5kURLe8E\\/MX4.QOgA0j.O.\",\"phone\":\"\",\"email\":\"\",\"bio\":\"\",\"u_type\":\"student\",\"profile_image\":\"189194924.png\",\"status\":\"enable\"},{\"message\":\"positive\",\"id\":\"83\",\"fullname\":\"varma_sir\",\"reg_num\":\"367855\",\"username\":\"varma_sir\",\"password\":\"$2y$10$bKl4pisl5f7BIqWwN5UtS.uwpB17udvUA5Rh73mKrSGfXkq67SKaS\",\"phone\":\"\",\"email\":\"\",\"bio\":\"\",\"u_type\":\"teacher\",\"profile_image\":\"189194924.png\",\"status\":\"enable\"},{\"message\":\"positive\",\"id\":\"82\",\"fullname\":\"sharma_sir\",\"reg_num\":\"188735\",\"username\":\"sharma_sir\",\"password\":\"$2y$10$dlTDetf\\/OIpJ2a1vdJ7mEeaYll53JPkVEw4k438C26I0MzeF57\\/Pu\",\"phone\":\"\",\"email\":\"\",\"bio\":\"\",\"u_type\":\"teacher\",\"profile_image\":\"1794217931.jpg\",\"status\":\"enable\"},{\"message\":\"positive\",\"id\":\"81\",\"fullname\":\"Preetam Kumar\",\"reg_num\":\"536703\",\"username\":\"Preetam Kumar\",\"password\":\"$2y$10$\\/0gpGr2BqPvwWGCVSOG6F.h5ABFpbUrCC0dyofsmBVb1XGkg2tXFq\",\"phone\":\"\",\"email\":\"thepreek007@gmail.com\",\"bio\":\"\",\"u_type\":\"student\",\"profile_image\":\"1648251706.png\",\"status\":\"enable\"},{\"message\":\"positive\",\"id\":\"71\",\"fullname\":\"dhasu_sir\",\"reg_num\":\"457634\",\"username\":\"dhasu_sir\",\"password\":\"$2y$10$rY5PW\\/t6bBQJn0dtGjYURO4VXEO34Hh8CQ0\\/dxwGHhhWh8rznxelG\",\"phone\":\"\",\"email\":\"\",\"bio\":\"hello your favourite dhasu sir here\",\"u_type\":\"teacher\",\"profile_image\":\"127157839.png\",\"status\":\"enable\"},{\"message\":\"positive\",\"id\":\"70\",\"fullname\":\"khan_sir\",\"reg_num\":\"283771\",\"username\":\"khan_sir\",\"password\":\"$2y$10$EGRP3pmVL.Ud\\/bNHV77druhz0G\\/scfcljfJ\\/Jh518Fz.8.2vwo5XS\",\"phone\":\"9636362995\",\"email\":\"khansir@gmail.com\",\"bio\":\"my name is khan\",\"u_type\":\"teacher\",\"profile_image\":\"127157839.png\",\"status\":\"enable\"},{\"message\":\"positive\",\"id\":\"44\",\"fullname\":\"mahendra\",\"reg_num\":\"1016777\",\"username\":\"mahendra\",\"password\":\"$2y$10$.CbgQ4wLfAhzw3IMcY9nO.KB.8aJVhgTN.ucKUCBdlPNULUfBQA2m\",\"phone\":\"\",\"email\":\"\",\"bio\":\"\",\"u_type\":\"student\",\"profile_image\":\"1392548983.png\",\"status\":\"enable\"},{\"message\":\"positive\",\"id\":\"43\",\"fullname\":\"preetam\",\"reg_num\":\"1025148\",\"username\":\"preetam\",\"password\":\"$2y$10$qQ9KJc2l1lXVKITCBvK\\/Ve2pXh4nhQCNw6Inxo6cBQOHOPl2SvK7i\",\"phone\":\"\",\"email\":\"\",\"bio\":\"\",\"u_type\":\"student\",\"profile_image\":\"928998975.png\",\"status\":\"enable\"},{\"message\":\"positive\",\"id\":\"42\",\"fullname\":\"bhanupratap\",\"reg_num\":\"1076415\",\"username\":\"bhanupratap\",\"password\":\"$2y$10$\\/TdXuVCiZX6XqCliO43Oi.7pPovsKarsr49UlqHPmzq3.Ei3Kenja\",\"phone\":\"\",\"email\":\"\",\"bio\":\"\",\"u_type\":\"student\",\"profile_image\":\"1406992643.jpg\",\"status\":\"enable\"},{\"message\":\"positive\",\"id\":\"41\",\"fullname\":\"pramod\",\"reg_num\":\"1060014\",\"username\":\"pramod\",\"password\":\"$2y$10$YuUAVEzFwvrFt7bnuidw2.\\/rRZXDKaUEGP5R8Uc9ym4S67ahq\\/WQG\",\"phone\":\"\",\"email\":\"\",\"bio\":\"\",\"u_type\":\"student\",\"profile_image\":\"928998975.png\",\"status\":\"enable\"},{\"message\":\"positive\",\"id\":\"40\",\"fullname\":\"rajendra\",\"reg_num\":\"1088519\",\"username\":\"rajendra\",\"password\":\"$2y$10$VApsmkdZgMpF9GW50JL9EOtXeUDb.SVW1MOuAmvKfFaRPgxvKNO86\",\"phone\":\"\",\"email\":\"\",\"bio\":\"\",\"u_type\":\"student\",\"profile_image\":\"955813546.jpg\",\"status\":\"enable\"},{\"message\":\"positive\",\"id\":\"39\",\"fullname\":\"rajulal\",\"reg_num\":\"1038117\",\"username\":\"rajulal\",\"password\":\"$2y$10$A.TO3CQtSrF4kQdjv9N9yOBVTU5\\/1rwvUMQSY42YwRAFXYmHH\\/9yK\",\"phone\":\"\",\"email\":\"\",\"bio\":\"\",\"u_type\":\"student\",\"profile_image\":\"1826759578.png\",\"status\":\"enable\"},{\"message\":\"positive\",\"id\":\"38\",\"fullname\":\"akshat\",\"reg_num\":\"1087776\",\"username\":\"akshat\",\"password\":\"$2y$10$aJnMsWnkSA2iqjmzfbbhEufF\\/DshyAA2oLDQR18u53dEpGa\\/v37Q2\",\"phone\":\"\",\"email\":\"\",\"bio\":\"\",\"u_type\":\"student\",\"profile_image\":\"140311673.png\",\"status\":\"enable\"},{\"message\":\"positive\",\"id\":\"37\",\"fullname\":\"sanju\",\"reg_num\":\"1088487\",\"username\":\"sanju\",\"password\":\"$2y$10$7Vx.11BYbHVQo75eTUK07erG7vYPy8VzC7RKjOdmx5RgRmiSOhH6S\",\"phone\":\"\",\"email\":\"\",\"bio\":\"\",\"u_type\":\"student\",\"profile_image\":\"1392548983.png\",\"status\":\"enable\"},{\"message\":\"positive\",\"id\":\"36\",\"fullname\":\"gaurav\",\"reg_num\":\"1009894\",\"username\":\"gaurav\",\"password\":\"$2y$10$A73klSu.TX1ccOL1TqNRIOeH\\/bFvlxHta2.Jv\\/CS54GU47dP2kynq\",\"phone\":\"\",\"email\":\"\",\"bio\":\"hello my name is gaurav\",\"u_type\":\"student\",\"profile_image\":\"1865856667.png\",\"status\":\"enable\"},{\"message\":\"positive\",\"id\":\"35\",\"fullname\":\"ankush\",\"reg_num\":\"1047944\",\"username\":\"ankush\",\"password\":\"$2y$10$g4Io0su5tOsugw8KRveLBe\\/9XCRO5wW9DCDgoMbiBR1htKgWzSkam\",\"phone\":\"\",\"email\":\"\",\"bio\":\"hey\",\"u_type\":\"student\",\"profile_image\":\"1336912770.png\",\"status\":\"disable\"},{\"message\":\"positive\",\"id\":\"34\",\"fullname\":\"viki\",\"reg_num\":\"1093909\",\"username\":\"viki\",\"password\":\"$2y$10$A75f0X0RjNLcY2bJoBveeu54y5nHpjXXOdY20waoLZLlvJnQxAPtq\",\"phone\":\"\",\"email\":\"\",\"bio\":\"\",\"u_type\":\"student\",\"profile_image\":\"1336912770.png\",\"status\":\"enable\"},{\"message\":\"positive\",\"id\":\"33\",\"fullname\":\"raju\",\"reg_num\":\"1028247\",\"username\":\"raju\",\"password\":\"$2y$10$CbNVg5fL5F.evr2IkXmIg.LQiXKVS84gF75e7eDT\\/x3PIJWYOSqOu\",\"phone\":\"\",\"email\":\"\",\"bio\":\"\",\"u_type\":\"student\",\"profile_image\":\"17974426.png\",\"status\":\"enable\"},{\"message\":\"positive\",\"id\":\"32\",\"fullname\":\"mahipal\",\"reg_num\":\"1093554\",\"username\":\"mahipal\",\"password\":\"$2y$10$1bckZNkeIOYeH2zI1CNvreDKu5L48p.a4yXP0YgzEWJjyvfhKgZLy\",\"phone\":\"\",\"email\":\"\",\"bio\":\"\",\"u_type\":\"student\",\"profile_image\":\"309898292.jpg\",\"status\":\"enable\"},{\"message\":\"positive\",\"id\":\"31\",\"fullname\":\"chandrapal singh\",\"reg_num\":\"1089909\",\"username\":\"chandrapal3000\",\"password\":\"$2y$10$JkOdck8RomFQf9gvlqfkUep6r3E1RRo5GFKXll9jHJiN6RoR150DS\",\"phone\":\"8306832308\",\"email\":\"chandrapal@gmail.com\",\"bio\":\"hey its me, chandrapal\",\"u_type\":\"student\",\"profile_image\":\"1186088052.jpg\",\"status\":\"enable\"}]";
    private final String DEFAULT_NOTES_RESPONSE2 = "[{\"message\":\"positive\",\"id\":\"52\",\"title\":\"This is test notes02\",\"notes_text\":\"\n\n" +
            "Hello i am under the water<\\/p>\\r\\n\",\"uploaded_by\":\"sharma_sir\",\"user_id\":\"82\",\"branch\":\"cs 1st semester\",\"subject\":\"1\",\"chapter\":\"2\",\"uploaded_time\":\"1 month ago\",\"status\":\"enable\",\"pinned\":\"no\"},{\"message\":\"positive\",\"id\":\"51\",\"title\":\"This is test notes01\",\"notes_text\":\"\n" +
            "\n" +
            "Hello i am under the water<\\/p>\\r\\n\",\"uploaded_by\":\"sharma_sir\",\"user_id\":\"82\",\"branch\":\"cs 1st semester\",\"subject\":\"1\",\"chapter\":\"2\",\"uploaded_time\":\"1 month ago\",\"status\":\"enable\",\"pinned\":\"no\"}]";
//    private final String DEFAULT_NOTES_RESPONSE = readTextFile("default_notes_response.txt");
    View root;
    FloatingActionButton floatingSearchButton;
    RecyclerView recyclerView;
    ProgressBar progressBar;
    Toolbar toolbar;
    Menu menu;
    MenuItem cardview, small_cardview, listview, column_view, search;


    @Override
    public void onCreate(@NonNull Bundle savedInstanceState) {
//        setHasOptionsMenu(true);

        super.onCreate(savedInstanceState);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        root = binding.getRoot();

        getLocation();

        floatingSearchButton = root.findViewById(R.id.floating_search_button_home_fragment);

        recyclerView = root.findViewById(R.id.recyclerView);
        progressBar = root.findViewById(R.id.progressBar);

        toolbar = binding.toolbarFragmentHome;
        menu = toolbar.getMenu();
        cardview = menu.findItem(R.id.action_cardview_home_menu);
        small_cardview = menu.findItem(R.id.action_small_cardview_home_menu);
        listview = menu.findItem(R.id.action_listview_home_menu);
        column_view = menu.findItem(R.id.action_column_view_home_menu);
        search = menu.findItem(R.id.action_search_home_menu);

        cardview.setVisible(false);
        small_cardview.setVisible(false);
        listview.setVisible(false);
        search.setVisible(false);
        column_view.setVisible(false);

        toolbar.setOnMenuItemClickListener(item -> {
            boolean temp = setItemClick(item);
            return temp;
        });
        toolbar.setOnMenuItemClickListener(this::setItemClick);

        notesHolder = new ArrayList<>();
        usersHolder = new ArrayList<>();

        progressBar.getIndeterminateDrawable().setColorFilter(0xFFFF0000, PorterDuff.Mode.MULTIPLY);

//        Users

         getUsersResponse();

//        Notes

        getNotesResponse();

        return root;
    }

    public void getUsersResponse(){
        Volley.newRequestQueue(requireActivity()).add(new StringRequest(Request.Method.POST, WEB_URL_GET_USERS, this::saveUsersToModel, error -> {
            usersResponse = "error";
            saveUsersToModel(DEFAULT_USER_RESPONSE);
            Toast.makeText(requireActivity(), error.toString().trim(), Toast.LENGTH_LONG).show();
        }){
            @NonNull
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> data = new HashMap<>();
                data.put("column", "");
                data.put("value", "");
                return data;
            }
        });
    }

    public void saveUsersToModel(String response){
        if(response.equals("Doesn't look like anything to me!") || response.equals("empty table/orderby/ascdesc!") || response.equals("oversmart mat ban, ja pucha h wo fill kar") ){
            Toast.makeText(requireActivity(),response,Toast.LENGTH_LONG).show();
        }else {
            try {

                JSONArray jsonArray = new JSONArray(response);
                message = jsonArray.getJSONObject(0).getString("message");
                if(message.equals("positive")) {

                    usersResponse = "success";

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        Model users = new Model();
                        users.setUsersId(jsonObject.getString("id"));
                        users.setUsersFullname(jsonObject.getString("fullname"));
                        users.setUsersRegNum(jsonObject.getString("reg_num"));
                        users.setUsersUsername(jsonObject.getString("username"));
                        users.setUsersPhone(jsonObject.getString("phone"));
                        users.setUsersEmail(jsonObject.getString("email"));
                        users.setUsersBio(jsonObject.getString("bio"));
                        users.setUsersType(jsonObject.getString("u_type"));
                        users.setUsersProfileImage(jsonObject.getString("profile_image"));
                        users.setUsersStatus(jsonObject.getString("status"));

                        usersHolder.add(users);
                    }
                } else {
                    Toast.makeText(getContext(), "No users found", Toast.LENGTH_LONG).show();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void getNotesResponse(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, WEB_URL_GET_NOTES, this::saveNotesToModel, error -> {
            progressBar.setVisibility(View.GONE);
            saveNotesToModel(readTextFile("default_notes_response.txt"));
//            saveNotesToModel(readTextFile("notes.txt"));
            if (isAdded()) {
                Toast.makeText(requireActivity(), error.toString().trim(), Toast.LENGTH_LONG).show();

                new MaterialAlertDialogBuilder(requireContext())
                        .setTitle("Connection error")
                        .setMessage("Could not connect to the server, We will show you offline data now, Make sure you are connected to the internet, if your internet is fine then that may be a server error")
                        .setCancelable(true)
                        .setPositiveButton("Try Again", (dialog, which) -> {
                            NavHostFragment.findNavController(com.chandrapal.manage_college.ui.home.HomeFragment.this)
                                    .navigate(R.id.go_to_HomeFragment);
                        })
                        .show();

            }
        }){
            @Nullable
            @org.jetbrains.annotations.Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> data = new HashMap<>();
                data.put("column", "status");
                data.put("value", "enable");
                return data;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(requireActivity());
        requestQueue.add(stringRequest);
    }

    public void saveNotesToModel(String response){
        if(response.equals("Doesn't look like anything to me!") || response.equals("empty table/orderby/ascdesc!") || response.equals("oversmart mat ban, ja pucha h wo fill kar") ){
            progressBar.setVisibility(View.GONE);
            Toast.makeText(requireActivity(),response,Toast.LENGTH_LONG).show();
        }else {
            try {

                JSONArray jsonArray = new JSONArray(response);
                message = jsonArray.getJSONObject(0).getString("message");
                if(message.equals("positive")) {

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        Model notes = new Model();
                        notes.setNotesId(jsonObject.getString("id"));
                        notes.setNotesTitle(jsonObject.getString("title"));
                        notes.setNotesNotesText(jsonObject.getString("notes_text"));
                        notes.setNotesUploadedBy(jsonObject.getString("uploaded_by"));

                        String notesUserIdString = jsonObject.getString("user_id");
                        String notesUserProfileImageString = "";

                        for(int j = 0; j < usersHolder.size(); j++){

                            if(notesUserIdString.equals(usersHolder.get(j).getUsersId())){
                                notesUserProfileImageString = usersHolder.get(j).getUsersProfileImage();
                            }

                        }

                        if(notesUserProfileImageString.equals("")){
                            notesUserProfileImageString = "1406992643.jpg";
                        }

                        notes.setNotesUserId(notesUserIdString);
                        notes.setNotesUserProfileImage(DIR+notesUserProfileImageString);
                        notes.setNotesBranch(jsonObject.getString("branch"));
                        notes.setNotesSubject(jsonObject.getString("subject"));
                        notes.setNotesChapter(jsonObject.getString("chapter"));
                        notes.setNotesUploadedTime(jsonObject.getString("uploaded_time"));
                        notes.setNotesStatus(jsonObject.getString("status"));
                        notes.setNotesPinned(jsonObject.getString("pinned"));

                        notesHolder.add(notes);
                    }
                    progressBar.setVisibility(View.GONE);
                    recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1, GridLayoutManager.VERTICAL, false));
                    adapterHome = new AdapterHome(notesHolder, this, this);
                    recyclerView.setAdapter(adapterHome);
                } else {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(getContext(), "No data found", Toast.LENGTH_LONG).show();
                    new MaterialAlertDialogBuilder(requireContext())
                            .setTitle("Server response")
                            .setMessage("Looks like database is empty!, No data found")
                            .setCancelable(true)
                            .setPositiveButton("Try Again", (dialog, which) -> {
                                NavHostFragment.findNavController(com.chandrapal.manage_college.ui.home.HomeFragment.this)
                                        .navigate(R.id.go_to_HomeFragment);
                            })
                            .show();
                }
                small_cardview.setVisible(true);
                search.setVisible(true);
                floatingSearchButton.setVisibility(View.VISIBLE);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public boolean setItemClick(MenuItem item){
            if(item.getItemId()==R.id.action_cardview_home_menu){
                Toast.makeText(requireActivity(),"Arranging results in card view", Toast.LENGTH_SHORT).show();
                recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1, GridLayoutManager.VERTICAL, false));
                adapterHome = new AdapterHome(notesHolder, this, this);
                recyclerView.setAdapter(adapterHome);
                adapterHome.notifyRecyclerview(1, 25, true, true, true);
                cardview.setVisible(false);
                small_cardview.setVisible(true);
            }
            if(item.getItemId()==R.id.action_small_cardview_home_menu){
                Toast.makeText(requireActivity(),"Arranging results in small card view", Toast.LENGTH_SHORT).show();
                adapterHome.notifyRecyclerview(0, 25, true, true, true);
                small_cardview.setVisible(false);
                listview.setVisible(true);
            }
            if(item.getItemId()==R.id.action_listview_home_menu){
                Toast.makeText(requireActivity(),"Arranging results in list view", Toast.LENGTH_SHORT).show();
                adapterHome.notifyRecyclerview(0, 25, false, false, false);
                listview.setVisible(false);
                column_view.setVisible(true);
            }
            if(item.getItemId()==R.id.action_column_view_home_menu){
                Toast.makeText(requireActivity(),"Arranging results in column view", Toast.LENGTH_SHORT).show();
                recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2, GridLayoutManager.VERTICAL, false));
                adapterHome = new AdapterHome(notesHolder, this, this);
                recyclerView.setAdapter(adapterHome);
                adapterHome.notifyRecyclerview(0, 20, false, false, false);
                column_view.setVisible(false);
                cardview.setVisible(true);
            }
            if(item.getItemId()==R.id.action_about_me_always_home_menu){
                Toast.makeText(getContext(),"Visit my website", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent("android.intent.action.VIEW", Uri.parse("http://chandrapal.gq"));
                startActivity(intent);
            }
            if(item.getItemId()==R.id.action_settings_home_menu){
                Toast.makeText(requireActivity(),"Opening settings", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getContext(), SettingsActivity.class);
                startActivity(intent);
            }
            if(item.getItemId()==R.id.action_faq_home_menu){
                Toast.makeText(requireActivity(),"Frequently asked questions", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent("android.intent.action.VIEW", Uri.parse("https://manage-college.000webhostapp.com/contact.php"));
                startActivity(intent);
            }
            if(item.getItemId()==R.id.action_about_us_home_menu){
                Toast.makeText(getContext(),"Visit my website", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent("android.intent.action.VIEW", Uri.parse("http://chandrapal.gq"));
                startActivity(intent);
            }
            if(item.getItemId()==R.id.action_visit_website_home_menu){
                Toast.makeText(requireActivity(),"Visit our website", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent("android.intent.action.VIEW", Uri.parse("https://manage-college.000webhostapp.com"));
                startActivity(intent);
            }
            if(item.getItemId()==R.id.action_see_notes_on_website_home_menu){
                Toast.makeText(requireActivity(),"See notes on website", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent("android.intent.action.VIEW", Uri.parse("https://manage-college.000webhostapp.com/all_notes.php"));
                startActivity(intent);
            }
            if(item.getItemId()==R.id.action_see_website_in_the_app_home_menu){
                Toast.makeText(requireActivity(),"See website here", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getContext(), WebViewActivity.class);
                intent.putExtra("link", "https://manage-college.000webhostapp.com");
                startActivity(intent);
            }
            if(item.getItemId()==R.id.action_search_home_menu){
                Toast.makeText(requireActivity(),"You clicked on search", Toast.LENGTH_SHORT).show();

                android.widget.SearchView searchView = (android.widget.SearchView) item.getActionView();
                searchView.setIconifiedByDefault(false);
                searchView.requestFocus();

                searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener(){
                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String newText) {
                        adapterHome.getFilter().filter(newText);
                        return false;
                    }
                });
            }
            return false;
    }

    private String readTextFile(String file){
        Log.d("readTextFile", "readTextFile: started");
        BufferedReader reader = null;
        StringBuilder builder = new StringBuilder();
        try {
            reader = new BufferedReader(new InputStreamReader(getContext().getAssets().open(file)));
            String line = "";

            while ((line = reader.readLine()) != null) {
                builder.append(line);
                Log.d("readTextFile", "readTextFile: reading : "+line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null){
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        Log.d("readTextFile", "readTextFile: finished");
        return builder.toString();
    }

    public void test(){
        Volley.newRequestQueue(requireContext()).add(new StringRequest(Request.Method.POST, WEB_URL_GET_NOTES, this::saveNotesToModel, this::showError){});
        new AdapterStudentsDashboardFragment.onImageClickInterfaceForStudents() {
            @Override
            public void onImageClickMethodForStudents(int position) {
                showPosition(position);
            }
        };
    }

    public void showPosition(Integer position){
        Log.d(TAG, "showPosition: "+position);
    }

    public void showError(VolleyError error){
        Log.d(TAG, "showError: "+error.toString());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onShowMoreClick(int position) {
        Intent intent = new Intent(getContext(), FullNotesActivity.class);
        intent.putExtra("notesId", notesHolder.get(position).getNotesId());
        intent.putExtra("notesTitle", notesHolder.get(position).getNotesTitle());
        intent.putExtra("notesNotesText", notesHolder.get(position).getNotesNotesText());
        intent.putExtra("notesUploadedBy", notesHolder.get(position).getNotesUploadedBy());
        intent.putExtra("notesUserId", notesHolder.get(position).getNotesUserId());
        intent.putExtra("notesUserProfileImage", notesHolder.get(position).getNotesUserProfileImage());
        intent.putExtra("notesBranch", notesHolder.get(position).getNotesBranch());
        intent.putExtra("notesSubject", notesHolder.get(position).getNotesSubject());
        intent.putExtra("notesChapter", notesHolder.get(position).getNotesChapter());
        intent.putExtra("notesUploadedTime", notesHolder.get(position).getNotesUploadedTime());
        intent.putExtra("notesStatus", notesHolder.get(position).getNotesStatus());
        intent.putExtra("notesPinned", notesHolder.get(position).getNotesPinned());
        startActivity(intent);
        requireActivity().overridePendingTransition(R.anim.nav_default_pop_enter_anim,R.anim.nav_default_pop_exit_anim);
    }

    @Override
    public void onProfileClick(int position) {

        Intent intent = new Intent(getContext(), UserProfileActivity.class);
        intent.putExtra("user_id", notesHolder.get(position).getNotesUserId());
        intent.putExtra("uploaded_by", notesHolder.get(position).getNotesUploadedBy());
        intent.putExtra("user_profile_image", notesHolder.get(position).getNotesUserProfileImage());

        startActivity(intent);
        requireActivity().overridePendingTransition(R.anim.nav_default_pop_enter_anim,R.anim.nav_default_pop_exit_anim);
    }

    double currentLatitude;
    double currentLongitude;
    double latitude = 26.934092650668997;
    double longitude = 75.74823570731044;
    private static final int RADIUS = 2000;

    public void getLocation(){
        GpsTracker gpsTracker = new GpsTracker(requireActivity());
        if(gpsTracker.canGetLocation()){
            currentLatitude = gpsTracker.getLatitude();
            currentLongitude = gpsTracker.getLongitude();

            Toast.makeText(requireActivity()," is : "+currentLatitude+" And Longitude : "+ currentLongitude,Toast.LENGTH_LONG).show();
            Log.d("f20-currentLatandlong", " is : "+ currentLatitude+","+currentLongitude);
            mapDistance(26.934092650668997, 75.74823570731044);
        }else{
            gpsTracker.showSettingsAlert();
        }
    }

    public void mapDistance(double latitude, double longitude){
        if(haveCurrentLatitudeAndLongitude()) {
            Location startPoint = new Location("locationA");
            startPoint.setLatitude(currentLatitude);
            startPoint.setLongitude(currentLongitude);

            Location endPoint = new Location("locationA");
            endPoint.setLatitude(latitude);
            endPoint.setLongitude(longitude);
            double distance=startPoint.distanceTo(endPoint);
            String distanceString = "";
            if(distance>1000)
                distanceString = distance/1000+"KM";
            else
                distanceString = distance+"M";
            Toast.makeText(requireActivity(),"Distance is : "+distance, Toast.LENGTH_LONG).show();
            Log.d("f20-latandlong", "is : "+latitude+","+longitude);
            Log.d("f20-distance", "mapDistance: "+distanceString);
            if(distance<=RADIUS) {
                Toast.makeText(requireActivity(), "Given location is within 2km radius of your current location", Toast.LENGTH_LONG).show();
                Log.d(TAG, "f20-givenLocationStatus : location is in 2km radius");
            }
            else {
                Toast.makeText(requireActivity(), "Given location is is not in 2km radius of your current location", Toast.LENGTH_LONG).show();
                Log.d(TAG, "f20-givenLocationStatus : location is not in 2km radius");
            }
        } else Log.d(TAG, "f20-latLongZero : zero values of latitude and longitude");

    }
    public boolean haveCurrentLatitudeAndLongitude(){
        return currentLatitude != 0 && currentLongitude != 0;
    }

    private double distanceBetweenTwoLocations(double lat1, double lon1, double lat2, double lon2) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1))
                * Math.sin(deg2rad(lat2))
                + Math.cos(deg2rad(lat1))
                * Math.cos(deg2rad(lat2))
                * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        return (dist);
    }

    private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }


}
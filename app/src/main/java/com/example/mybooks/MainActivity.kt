package com.example.mybooks

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.mybooks.databinding.ActivityMainBinding


public class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"
    private lateinit var binding: ActivityMainBinding


    var filteringArray = mutableListOf<BookItem>()
    var authors: MutableList<String> = mutableListOf<String>(
        "All","Chinua Achebe", "Hans Christian Andersen","Dante Alighieri","Unknown","Jane Austen","Honoré de Balzac","Samuel Beckett"
        ,"Giovanni Boccaccio","Jorge Luis Borges","Emily Brontë", "Albert Camus", "Paul Celan","Louis-Ferdinand Céline",
        "Miguel de Cervantes", "Geoffrey Chaucer", "Anton Chekhov", "Joseph Conrad", "Charles Dickens", "Denis Diderot",
        "Alfred Döblin","Fyodor Dostoevsky","George Eliot","Ralph Ellison","Euripides","William Faulkner","Gustave Flaubert",
        "Federico García Lorca","Gabriel García Márquez","Johann Wolfgang von Goethe","Nikolai Gogol","Günter Grass",
        "João Guimarães Rosa","Knut Hamsun","Ernest Hemingway","Homer","Henrik Ibsen","James Joyce","Franz Kafka","Kālidāsa",
        "Yasunari Kawabata","Nikos Kazantzakis","D. H. Lawrence","Halldór Laxness","Giacomo Leopardi","Doris Lessing",
        "Astrid Lindgren","Lu Xun","Naguib Mahfouz","Thomas Mann","Herman Melville","Michel de Montaigne","Elsa Morante",
        "Toni Morri","Murasaki Shikibu","Robert Musil","Vladimir Nabokov","George Orwell","Ovid","Fernando Pessoa","Edgar Allan Poe",
        "Marcel Proust","François Rabelais","Juan Rulfo","Rumi","Salman Rushdie","Saadi","Tayeb Salih","José Saramago",
        "William Shakespeare","Sophocles","Stendhal","Laurence Sterne","Italo Svevo","Jonathan Swift","Leo Tolstoy","Mark Twain",
        "Valmiki","Virgil","Vyasa","Walt Whitman","Virginia Woolf","Marguerite Yourcenar"
    )
    var languages2: MutableList<String> = mutableListOf("All","English","Danish","Italian","Akkadian","Hebrew","Arabic",
        "Old Norse","French","German","Spanish","Russian","Norwegian","Greek","Japanese","Sanskrit","Icelandic","Swedish","Chinese",
        "Classical Latin","Persian","Portuguese")

    var selectedLang =languages2[0];
    var selectAuthor = authors[0]

    lateinit var viewModel: MainViewModel

    private val retrofitService = RetrofitService.getInstance()
    val adapter = MainAdapter()

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.autoCompleteTextView.setText(languages2[0])
        binding.autoCompleteTextView2.setText(authors[0])

        val arrayAdapter = ArrayAdapter(this , R.layout.drop_down, languages2)
        binding.autoCompleteTextView.setAdapter(arrayAdapter)

        selectedLang = binding.autoCompleteTextView.toString()

        binding.autoCompleteTextView.onItemClickListener =
            AdapterView.OnItemClickListener { parent, view, position, id ->
                selectedLang = languages2[position]

//                if(selectAuthor == authors[0]) {
//                    filteringArray =
//                        adapter.reserveList.filter { s -> s.language == selectedLang } as MutableList<BookItem>
//                    adapter.setItemList(filteringArray)
//                }else{
//                    filteringArray =
//                        filteringArray.filter { s -> s.language == selectedLang } as MutableList<BookItem>
//                    adapter.setItemList(filteringArray)
//                }

                filteringArray =
                        adapter.reserveList.filter { s -> s.language == selectedLang } as MutableList<BookItem>
                    adapter.setItemList(filteringArray)

            }

        val arrayAdapter2 = ArrayAdapter(this , R.layout.drop_down, authors)
        binding.autoCompleteTextView2.setAdapter(arrayAdapter2)

        selectAuthor = binding.autoCompleteTextView2.toString()

        binding.autoCompleteTextView2.onItemClickListener =
            AdapterView.OnItemClickListener{ parent, view, position, id ->
                selectAuthor = authors[position]

//                if(selectedLang == languages2[0]){
//                    filteringArray = adapter.reserveList.filter { s -> s.author == selectAuthor } as MutableList<BookItem>
//                    adapter.setItemList(filteringArray)
//                }else{
//                    filteringArray =
//                        filteringArray.filter { s -> s.author == selectAuthor } as MutableList<BookItem>
//                    adapter.setItemList(filteringArray)
//                }

                filteringArray = filteringArray.filter { s -> s.author == selectAuthor } as MutableList<BookItem>
                    adapter.setItemList(filteringArray)

            }



        viewModel = ViewModelProvider(this, MyViewModelFactory(MainRepository(retrofitService))).get(MainViewModel::class.java)

        binding.recyclerview.adapter = adapter

        viewModel.itemList.observe(this, Observer {
            //Log.d(TAG, "onCreate: $it")
                adapter.setItemList(it)
                adapter.setReserveItemList(it)
        })

        viewModel.errorMessage.observe(this, Observer {

        })
        viewModel.getAllItems()
    }


}
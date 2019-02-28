package lizka.reminder

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.FragmentManager
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import lizka.reminder.adapter.TabAdapter
import lizka.reminder.db.dao.TaskDao
import lizka.reminder.dialog.AddingTaskDialogFragment
import lizka.reminder.fragment.CurrentTaskFragment
import lizka.reminder.fragment.DoneTaskFragment
import lizka.reminder.fragment.SplashFragment
import lizka.reminder.model.ModelTask
import lizka.reminder.model.ModelTask.STATUS_DONE
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity(), AddingTaskDialogFragment.AddingTaskListener {


    lateinit var daoInterface:TaskDao
    lateinit var application: ReminderApplication
    lateinit var fragmentManager: FragmentManager

    lateinit var preferenceHelper:  PreferenceHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        PreferenceHelper.getInstance().init(applicationContext)
        preferenceHelper = PreferenceHelper.getInstance()
        application = getApplication() as ReminderApplication
        daoInterface = application.db.taskDao()
        fragmentManager = supportFragmentManager

        runSplash()

        setUI()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action if it is present
        menuInflater.inflate(R.menu.menu_main, menu)
        val splashItem = menu.findItem(R.id.action_splash)
        splashItem.isChecked = preferenceHelper.getBoolean(PreferenceHelper.SPLASH_IS_INVISIBLE)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        val id = item.itemId

        // меняем значение по нажатию
        if (id == R.id.action_splash) {
            item.isChecked = !item.isChecked
            preferenceHelper.putBoolean(PreferenceHelper.SPLASH_IS_INVISIBLE, item.isChecked)
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    // метод запуска splashscreen
    private fun runSplash() {

        if (!preferenceHelper.getBoolean(PreferenceHelper.SPLASH_IS_INVISIBLE)) {

            val splashFragment = SplashFragment()

            // отображение фрагмента
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame, splashFragment)
                    .addToBackStack(null)
                    .commit()

        }
    }

    private lateinit var tabAdapter: TabAdapter

    private lateinit var currentTaskFragment: CurrentTaskFragment

    private lateinit var doneTaskFragment: DoneTaskFragment

    private fun setUI() {
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        if (toolbar != null) {
            toolbar.setTitleTextColor(getColor(R.color.white))
            setSupportActionBar(toolbar)
        }

        val tabLayout = findViewById<TabLayout>(R.id.tab_layout)
        tabLayout.addTab(tabLayout.newTab().setText(R.string.current_task))
        tabLayout.addTab(tabLayout.newTab().setText(R.string.done_task))

        val viewPager = findViewById<ViewPager>(R.id.pager)
        tabAdapter = TabAdapter(fragmentManager, 2)

        viewPager.adapter = tabAdapter
        viewPager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))

        tabLayout.setOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                viewPager.currentItem = tab.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}

            override fun onTabReselected(tab: TabLayout.Tab) {}
        })

        currentTaskFragment = tabAdapter.getItem(TabAdapter.CURRENT_TASK_FRAGMENT_POSITION) as CurrentTaskFragment
        doneTaskFragment = tabAdapter.getItem(TabAdapter.DONE_TASK_FRAGMENT_POSITION) as DoneTaskFragment

        // вешаем на него слушателя

        fab.setOnClickListener {
            val addingTaskDialogFragment = AddingTaskDialogFragment()
            addingTaskDialogFragment.show(fragmentManager, "AddingTaskDialogFragment")
        }
    }

    override fun createdTask(newTask: ModelTask) {

        Log.i("BUGG", "title = ${newTask.title}")
        val notifyIntent = Intent(this, MyReceiver::class.java)
        notifyIntent.putExtra("taskText", newTask.title)

        val pendingIntent = PendingIntent.getBroadcast(this, 0, notifyIntent, 0)
        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        newTask.date
        alarmManager.set(AlarmManager.RTC_WAKEUP, newTask.time, pendingIntent)
        val dateString = SimpleDateFormat("dd/MM/yyyy").format(Date(newTask.time))
        val time = SimpleDateFormat("HH:mm:ss").format(Date(newTask.time))
        Log.i("BUGG", "data: = $dateString" + "time = $time")
        addCurrentTask(newTask)
    }

    override fun onTaskAddingCancel() {}

    fun addDoneTask(task: ModelTask) {
        doneTaskFragment.addTask(task)
        GlobalScope.launch(Dispatchers.Main){
            withContext(Dispatchers.IO){
                daoInterface.delete(task)
                daoInterface.insert(task)
            }
        }
        currentTaskFragment.addTask(task)

    }

    fun addCurrentTask(task: ModelTask) {
        GlobalScope.launch(Dispatchers.Main){
            withContext(Dispatchers.IO){
                daoInterface.delete(task)
                daoInterface.insert(task)
            }
        }
        currentTaskFragment.addTask(task)
    }

    fun removeTask(task: ModelTask) {
        GlobalScope.launch(Dispatchers.Main){
            withContext(Dispatchers.IO){
                daoInterface.delete(task)
            }
        }
    }

    fun getAllCurrent(callBackUI: (MutableList<ModelTask>) -> Unit){
        GlobalScope.launch(Dispatchers.Main){
            val allList = getAll()
            val filteredList = allList.filter { it.status != STATUS_DONE}.toMutableList()
            callBackUI(filteredList)
        }
    }

    fun getAllDone(callBackUI: (MutableList<ModelTask>) -> Unit){
        GlobalScope.launch(Dispatchers.Main){
            val allList = getAll()
            val filteredList = allList.filter { it.status == STATUS_DONE}.toMutableList()
            callBackUI(filteredList)
        }
    }

    private suspend fun getAll():List<ModelTask> = withContext(Dispatchers.IO){
        daoInterface.getAll()
    }
}
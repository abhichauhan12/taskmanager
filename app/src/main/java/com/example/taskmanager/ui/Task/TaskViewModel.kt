import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.taskmanager.data.entities.Task
import com.example.taskmanager.domain.repo.TaskRepository
import com.example.taskmanager.utils.SettingsPrefsConstants
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class TaskViewModel(
    private val taskRepository: TaskRepository
) : ViewModel() {


    val showCompleted = taskRepository.showCompleted
    val sortByPriority =taskRepository.sortByPriority
    val sortByDeadline = taskRepository.sortByDeadline

    private val allTasks = taskRepository.getTasks()

    private val itemToBeDeleted : HashSet<Int> = HashSet()
    private val itemToBeMarkAsCompleted : HashMap<Int,Boolean> = HashMap()

  val tasks = combine(showCompleted,sortByPriority,sortByDeadline,allTasks){showCompleted ,showPriority,showDeadline,allTasks->
        when{
            showDeadline && showPriority ->taskRepository.getCompletedPriorityDeadlineTask(showCompleted=showCompleted)
            showDeadline -> taskRepository.getDeadlineSort(showCompleted)
            showPriority -> taskRepository.getPriorityInc(showCompleted)
            else -> taskRepository.getTasks(showCompleted)
        }
    }


/*
    private fun insertTasksInDb() {
        viewModelScope.launch {
           for (taskEntity in taskList) {
                taskRepository.insertTask(taskEntity)
            }
        }
    }
*/

    fun updateShowCompleted(showCompleted : Boolean){
        viewModelScope.launch {
            taskRepository.updateShowCompleted(showCompleted)
        }
    }
    fun updateShowPriority(showPriority : Boolean){
        viewModelScope.launch {
            taskRepository.updateShowPriority(showPriority)
        }
    }
    fun updateShowDeadline(showDeadline : Boolean){
        viewModelScope.launch {
            taskRepository.updateShowDeadline(showDeadline)
        }
    }

    fun showTasks()=taskRepository.getTasks()

    fun delete(){
        viewModelScope.launch {
            itemToBeDeleted.forEach {
                taskRepository.deleteTask(id = it)
            }
        }
    }

    fun updateItemToBeDeleted(id: Int){
        if (itemToBeDeleted.contains(id)){
            itemToBeDeleted.remove(id)
        }else{
            itemToBeDeleted.add(id)
        }
    }



    fun updateItemToBeMarkAsCompleted(id: Int,completed: Boolean){
        if(itemToBeMarkAsCompleted.containsKey(id)){
            itemToBeMarkAsCompleted.remove(id)
        }else{
            itemToBeMarkAsCompleted.put(id,!completed)
        }
    }



    fun markAsCompleted(){
        viewModelScope.launch {
            itemToBeMarkAsCompleted.forEach { (id, completed)  ->
                taskRepository.toggleCompleted(completed,id)
            }
            itemToBeMarkAsCompleted.clear()
        }
    }


    class Factor(private val taskRepository: TaskRepository):ViewModelProvider.Factory{
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return TaskViewModel(taskRepository) as T
        }
    }

}
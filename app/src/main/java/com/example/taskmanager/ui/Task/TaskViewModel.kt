import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.taskmanager.domain.repo.TaskRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class TaskViewModel(
    private val taskRepository: TaskRepository
) : ViewModel() {

    init {
//        insertTasksInDb()
    }
    private val showCompleted = MutableStateFlow(false)
    private val showPriority = MutableStateFlow(false)
    private val showDeadline = MutableStateFlow(false)


  val tasks = combine(showCompleted,showPriority,showDeadline){showCompleted ,showPriority,showDeadline->
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
        this.showCompleted.value = showCompleted
    }
    fun updateShowPriority(showPriority : Boolean){
        this.showPriority.value = showPriority
    }
    fun updateShowDeadline(showDeadline : Boolean){
        this.showDeadline.value = showDeadline
    }

    fun showTasks()=taskRepository.getTasks()

    fun delete(id : Int){
        viewModelScope.launch {
            taskRepository.deleteTask(id = id)
        }
    }


    class Factor(private val taskRepository: TaskRepository):ViewModelProvider.Factory{
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return TaskViewModel(taskRepository) as T
        }
    }

}
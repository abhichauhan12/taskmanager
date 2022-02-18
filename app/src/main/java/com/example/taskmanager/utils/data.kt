package com.example.taskmanager.utils

import com.example.taskmanager.data.entities.TaskEntitiy

val taskList= listOf<TaskEntitiy>(
    TaskEntitiy("do something",2,"1/jan",completed = false),
    TaskEntitiy("adabns",1,"1/jan",completed = false),
    TaskEntitiy("do code",1,"2/jan",completed = false),
    TaskEntitiy("eat",2,"3/jan",completed = true),
    TaskEntitiy("do do ",1,"4/jan",completed = false),
    TaskEntitiy("sleep",3,"5/jan",completed = false),
    TaskEntitiy("dance",3,"1/jan",completed = true)


)
/***
 *  entity = title,subtitle,priority, deadline, completed
 */
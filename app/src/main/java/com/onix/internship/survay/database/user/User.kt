package com.onix.internship.survay.database.user


import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.room.*
import com.onix.internship.survay.BR
import com.onix.internship.survay.common.Role

@Entity(tableName = "users", indices = [Index(value = ["login"], unique = true)])
data class User(

    @PrimaryKey(autoGenerate = true) private var uid: Int = 0,
    @ColumnInfo(name = "login") private var login: String = "",
    @ColumnInfo(name = "first_name") private var firstName: String = "",
    @ColumnInfo(name = "second_name") private var secondName: String = "",
    @ColumnInfo(name = "password") private var password: String = "",
    @Ignore private var passwordConfirm: String = "",
    @ColumnInfo(name = "role") private var role: Int = -1

) : BaseObservable() {

    @Ignore
    private var _listener: Change? = null

    @Bindable
    fun getUid(): Int = uid

    fun setUid(value: Int) {
        uid = value
        notifyPropertyChanged(BR.uid)
    }

    @Bindable
    fun getLogin(): String = login

    fun setLogin(value: String) {
        login = value
        notifyPropertyChanged(BR.login)
    }

    @Bindable
    fun getFirstName(): String = firstName

    fun setFirstName(value: String) {
        firstName = value
        notifyPropertyChanged(BR.firstName)
    }

    @Bindable
    fun getSecondName(): String = secondName

    fun setSecondName(value: String) {
        secondName = value
        notifyPropertyChanged(BR.secondName)
    }

    @Bindable
    fun getPassword(): String = password

    fun setPassword(value: String) {
        password = value
        notifyPropertyChanged(BR.password)
    }

    @Bindable
    fun getPasswordConfirm(): String = passwordConfirm

    fun setPasswordConfirm(value: String) {
        passwordConfirm = value
        notifyPropertyChanged(BR.passwordConfirm)
    }

    @Bindable
    fun getRole(): Int = role

    fun setRole(value: Int) {
        role = value
        notifyPropertyChanged(BR.role)
    }


    fun getRoleEnum(): Role = when (getRole()) {
        0 -> Role.ADMIN
        1 -> Role.MANAGER
        2 -> Role.USER
        else -> Role.DEFAULT
    }

    fun setRoleEnum(role: Role){
        setRole(role.roleIndex)
    }

    fun changeRole(){
        if(getRoleEnum() == Role.MANAGER){
            setRoleEnum(Role.USER)
        } else {
            setRoleEnum(Role.MANAGER)
        }
        _listener!!.onTextChange(this)
    }

    fun setOnChangeListener(change: Change) {
        _listener = change
    }

}

interface Change {
    fun onTextChange(value: User)
}

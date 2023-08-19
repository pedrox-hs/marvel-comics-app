package com.example.comics.presenter

import com.example.comics.domain.entity.Comic

interface IPresenter {

    fun setupList(list: List<Comic>)

    fun error()
}
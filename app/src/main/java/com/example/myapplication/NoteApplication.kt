package com.example.myapplication

import kotlin.text.Typography.dagger

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class NoteApplication : Application()
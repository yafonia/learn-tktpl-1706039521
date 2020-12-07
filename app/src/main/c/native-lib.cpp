//
// Created by Yafonia Hutabarat on 2020-12-07.
//

#include <jni.h>
#include <stdlib.h>
#include <time.h>


extern "C"
JNIEXPORT jint JNICALL
Java_id_ac_ui_cs_mobileprogramming_yafonia_helloworld_MainActivity_randomizeNumber(JNIEnv *env, jobject thiz) {
    srand((unsigned int) time(0));
    int intrandom = (rand() % (990 - 101)) + 101;
    return intrandom;
}
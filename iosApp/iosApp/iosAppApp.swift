//
//  iosAppApp.swift
//  iosApp
//
//  Created by farook on 15/06/26.
//

import SwiftUI
import shared
@main
struct iosAppApp: App {
    
    init(){
            KoinInitializerKt.doInitKoin()
      }
    
    
    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}

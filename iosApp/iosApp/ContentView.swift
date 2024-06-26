import UIKit
import SwiftUI
import ComposeApp

struct ComposeView: UIViewControllerRepresentable {
    func makeUIViewController(context: Context) -> UIViewController {
        let controller = MainViewControllerKt.MainViewController()
        setupStatusBar(view: controller.view)
        return controller
    }

    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {}

    // Change the status bar color to the primary color
    private func setupStatusBar(view: UIView) {
            if #available(iOS 13.0, *) {
                let window = UIApplication.shared.windows.first
                let topPadding = window?.safeAreaInsets.top
                let statusBar = UIView(frame: CGRect(x: 0, y: 0, width: UIScreen.main.bounds.size.width, height: topPadding ?? 0.0))

                statusBar.backgroundColor = UIColor(red: 0, green: 0, blue: 0, alpha: 1.0)

                UIApplication.shared.windows.first?.addSubview(statusBar)
            } else {
                let statusBarColor = UIColor(red: 0, green: 0, blue: 0, alpha: 1.0)
                let statusbarView = UIApplication.shared.statusBarUIView
                statusbarView?.backgroundColor = statusBarColor
            }
        }
}

struct ContentView: View {
    var body: some View {
        ComposeView()
            .ignoresSafeArea(.keyboard) // Compose has its own keyboard handler
            .edgesIgnoringSafeArea(.bottom)
    }
}

extension UIApplication {
    var statusBarUIView: UIView? {
        if #available(iOS 13.0, *) {
            let tag = 38482
            let keyWindow = UIApplication.shared.windows.first

            if let statusBar = keyWindow?.viewWithTag(tag) {
                return statusBar
            } else {
                guard let statusBarFrame = keyWindow?.windowScene?.statusBarManager?.statusBarFrame else { return nil }
                let statusBarView = UIView(frame: statusBarFrame)
                statusBarView.tag = tag
                keyWindow?.addSubview(statusBarView)
                return statusBarView
            }
        } else {
            return value(forKey: "statusBar") as? UIView
        }
    }
}

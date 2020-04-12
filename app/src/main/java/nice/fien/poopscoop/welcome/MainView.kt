package nice.fien.poopscoop.welcome

import android.content.Intent
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import nice.fien.poopscoop.R
import nice.fien.poopscoop.welcome.logic.WelcomeInjector
import kotlinx.android.synthetic.main.login_fragment.*
import nice.fien.poopscoop.login.LoginActivity

class MainView : Fragment() {

    companion object {
        fun newInstance() = MainView()
    }

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onStart() {
        super.onStart()
        // Probably an overly complex way of doing this.
        // Could just: viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        viewModel = ViewModelProviders.of(
            this,
            WelcomeInjector(requireActivity().application).provideMainViewModelFactory()
        ).get(MainViewModel::class.java)

        setupClickListeners()
        observeViewModel()
    }

    private fun setupClickListeners() {
        // login
        //sign up
        // top5
        btnLogin.setOnClickListener { onMoveToLogin() }
    }

    private fun observeViewModel() {
        // hjaista vidu
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        // TODO: Use the ViewModel
    }

    private fun onMoveToLogin() = requireActivity().startActivity(
        Intent(
            activity,
            LoginActivity::class.java
        )
    )

}
